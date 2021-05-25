package Demo.Graph;
import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class KruskalVisualizer {
    private int delay  = 100;
    private GraphData data;
    private GraphFrame frame;
    private boolean pause;

    private Vertex v1 = new Vertex(300, 370, 1),
            v2 = new Vertex(400, 170, 2),
            v3 = new Vertex(210, 270, 3),
            v4 = new Vertex(400, 370, 4),
            v5 = new Vertex(490, 270, 5),
            v6 = new Vertex(300, 170, 6);

    public KruskalVisualizer(int sceneWidth, int sceneHeight) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        Collections.addAll(vertices, v1, v2, v3, v4, v5,v6);

        data = new GraphData(vertices, false, "kruskal", true);
        EventQueue.invokeLater(() -> {
            frame = new GraphFrame("Kruskal", sceneWidth, sceneHeight);
            frame.control.remove(frame.startVertex);
            frame.control.remove(frame.startVertexInput);
            frame.run.addActionListener(new RunHandler());
            frame.newGraph.addActionListener(new NewGraphHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void kruskal() {
        GraphData.Edge[] edges = new GraphData.Edge[data.numOfEdges()];
        int[] ends = new int[data.n];
        int k = 0;
        for(int i = 0; i < data.n; i++)
            for(int j = i + 1; j < data.n; j++)
               if(data.edges[i][j] != 0) {
                   Vertex start = data.getVertex(i);
                   Vertex end = data.getVertex(j);
                   edges[k++] = new GraphData.Edge(start, end, data.edges[i][j]);
               }
        for(int i = 0; i < data.n; i++)
            ends[i] = i;
        //sort
        setData(2);
        Arrays.sort(edges);
        setData(3);
        int index = 0, n = 0;
        setData(4);
        while(n < data.n - 1) {
            Vertex start = edges[index].start;
            Vertex end = edges[index].end;
            setData(5);
            int sPos = data.getIndex(start.value);
            int ePos = data.getIndex(end.value);
            int sEnd = getEnd(ends, sPos);
            int eEnd = getEnd(ends, ePos);
            setData(6);
            if(sEnd != eEnd) {
                setData(7);
                data.visitedVertex.add(start);
                data.isVisited[sPos] = true;
                data.visitedVertex.add(end);
                data.isVisited[ePos] = true;
                int[] e = {start.value, end.value};
                data.visitedEdge.add(e);
                data.isPath[sPos][ePos] = true;
                data.isPath[ePos][sPos] = true;
                setData(8);
                data.cost += edges[index].weight;
                n++;
                setData(9);
                ends[eEnd] = sEnd;
            }
            setData(10);
            index++;
        }


    }

    private int getEnd(int[] ends, int v) {
        if(ends[v] != v) {
            return getEnd(ends, ends[v]);
        }
        return v;
    }

    private void setData(int curLine) {
        if(!pause) {
            data.curLine = curLine;
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
            if(isNew) {
                if (previousGraph != null && data == previousGraph) {
                    data.visitedVertex.clear();
                    data.visitedEdge.clear();
                    for (int i = 0; i < data.n; i++) {
                        for (int j = 0; j < data.n; j++)
                            data.isPath[i][j] = false;
                        data.isVisited[i] = false;
                    }
                    data.cost = 0;

                }
               thread =  new Thread(() -> {
                    setData(1);
                    kruskal();
                    setData(-1);
                   AlgoVisHelper.pause(1000);
                    isNew = true;
                    pause = false;
                    frame.run.setText("Run");
                });
                thread.start();
                isNew = false;
                frame.run.setText("Pause");
                previousGraph = data;
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

            if(thread != null && thread.isAlive())
                thread.stop();

            ArrayList<Vertex> vertices = new ArrayList<>();
            Collections.addAll(vertices, v1, v2, v3, v4, v5,v6);

            data = new GraphData(vertices, false,"kruskal", true);
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

