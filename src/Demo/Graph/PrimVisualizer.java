package Demo.Graph;
import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class PrimVisualizer {
    private int delay = 100;
    private GraphData data;
    private GraphFrame frame;
    private boolean pause;

    private Vertex v1 = new Vertex(300, 370, 1),
            v2 = new Vertex(400, 170, 2),
            v3 = new Vertex(210, 270, 3),
            v4 = new Vertex(400, 370, 4),
            v5 = new Vertex(490, 270, 5),
            v6 = new Vertex(300, 170, 6);

    public PrimVisualizer(int sceneWidth, int sceneHeight) {

        ArrayList<Vertex> vertices = new ArrayList<>();
        Collections.addAll(vertices, v1, v2, v3, v4, v5, v6);

        data = new GraphData(vertices, false,"prim", true);
        EventQueue.invokeLater(() -> {
            frame = new GraphFrame("Prim", sceneWidth, sceneHeight);
            frame.run.addActionListener(new RunHandler());
            frame.newGraph.addActionListener(new NewGraphHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void prim(int startIndex) {
        setData(2, -1, -1);
        Vertex startVertex = data.getVertex(startIndex);
        setData(3, -1, -1);
        data.isVisited[startIndex] = true;
        data.visitedVertex.add(startVertex);
        setData(4, -1, -1);
        for(int i = 1; i < data.n; i++) {
            int minCost = Integer.MAX_VALUE;
            int v = -1, w = -1;
            for(Vertex visited : data.visitedVertex) {
                int index = data.getIndex(visited.value);
                ArrayList<Integer> neighborsIndex = data.getNeighbors(index);
                for(int neighborIndex :  neighborsIndex) {
                    if(data.edges[index][neighborIndex] != 0
                            && data.edges[index][neighborIndex] < minCost
                            && !data.isVisited[neighborIndex]) {
                        minCost = data.edges[index][neighborIndex];
                        v = index;
                        w = neighborIndex;
                    }
                }
            }
            if(v != -1 && w != -1) {
                setData(5, v, w);
                data.cost += minCost;
                data.isPath[v][w] = true;
                data.isPath[w][v] = true;
                int[] edge = {data.getVertex(v).value, data.getVertex(w).value};
                data.visitedEdge.add(edge);
                setData(6, v, w);
                data.isVisited[w] = true;
                Vertex vertex = data.getVertex(w);
                data.visitedVertex.add(vertex);
                setData(7, -1, -1);
            }
        }
    }

    private void setData(int curLine, int umin, int vmin) {
        if(!pause) {
            data.curLine = curLine;
            data.umin = umin;
            data.vmin = vmin;
            frame.render(data);
            AlgoVisHelper.pause(delay);
        } else {
            synchronized (data) {
                try {
                    data.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    GraphData previousGraph;
    Thread thread;
    boolean isNew = true;
    private class RunHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.msg.setText("");

            if(isNew) {
                String s = frame.startVertexInput.getText();
                if(!s.equals(""))
                try {
                    int value = Integer.valueOf(s);
                    int startIndex = data.getIndex(value);
                    if(startIndex == -1) {
                        frame.msg.setText("vertex not valid!");
                        return;
                    }
                    if (previousGraph != null && data == previousGraph) {
                        data.visitedVertex.clear();
                        data.visitedEdge.clear();
                        for (int i = 0; i < data.n; i++) {
                            for (int j = 0; j < data.n; j++)
                                data.isPath[i][j] = false;
                            data.isVisited[i] = false;
                        }
                        data.cost = 0;
                        setData(-1, -1, -1);
                    }
                    thread = new Thread(() -> {
                        setData(1, -1, -1);
                        prim(startIndex);
                        setData(-1, -1, -1);
                        AlgoVisHelper.pause(1000);
                        isNew = true;
                        pause = false;
                        frame.run.setText("Run");
                        previousGraph = data;
                    });
                    thread.start();
                    isNew = false;
                    frame.run.setText("Pause");
                } catch (Exception exp) {
                    frame.msg.setText("vertex not valid!");
                }
            } else {
                if(frame.run.getText().equals("Run")) {
                    pause = false;
                    synchronized (data) {
                        data.notify();
                    }
                    frame.run.setText("Pause");
                } else {
                    pause = true;
                    frame.run.setText("Run");
                }
            }
        }
    }

    private class NewGraphHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            frame.msg.setText("");

            if(thread != null && thread.isAlive())
                thread.stop();

            ArrayList<Vertex> vertices = new ArrayList<>();
            Collections.addAll(vertices, v1, v2, v3, v4, v5, v6);

            data = new GraphData(vertices, false,"prim", true);
            isNew = true;
            pause = false;
            frame.run.setText("Run");
            frame.render(data);
        }
    }

    private class SpeedHandler implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if((JSlider) e.getSource() == frame.speed)
                delay = frame.speed.getValue();
        }
    }

}