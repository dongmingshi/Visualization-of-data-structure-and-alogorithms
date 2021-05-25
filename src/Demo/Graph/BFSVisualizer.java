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
import java.util.LinkedList;

public class BFSVisualizer {

    private int delay = 100;
    private GraphData data;
    private GraphFrame frame;
    private boolean pause;

    private Vertex v1 = new Vertex(260, 350, 1),
            v2 = new Vertex(320, 150, 2),
            v3 = new Vertex(210, 250, 3),
            v4 = new Vertex(380, 350, 4),
            v5 = new Vertex(430, 250, 5);

    public BFSVisualizer(int sceneWidth, int sceneHeight) {

        ArrayList<Vertex> vertices = new ArrayList<>();
        Collections.addAll(vertices, v1, v2, v3, v4, v5);

        data = new GraphData(vertices, false,"bfs", false);

        EventQueue.invokeLater(() -> {
            frame = new GraphFrame("Breadth First Search", sceneWidth, sceneHeight);
            frame.run.addActionListener(new RunHandler());
            frame.newGraph.addActionListener(new NewGraphHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }


    public void bfs(int index) {
        setData(2);
        data.isVisited[index] = true;
        data.visitedVertex.add(data.getVertex(index));
        LinkedList<Integer> queue = new LinkedList<>();
        setData(3);
        queue.add(index);
        setData(4);
        while(!queue.isEmpty()) {
            setData(5);
            int vertex = queue.remove();
            ArrayList<Integer> neighbors = data.getNeighbors(vertex);
            setData(6);
            for(int neighbor: neighbors) {
                setData(7);
                if(!data.isVisited[neighbor]) {
                    setData(8);
                    data.isVisited[neighbor] = true;
                    data.isPath[vertex][neighbor] = true;
                    data.isPath[neighbor][vertex] = true;
                    data.visitedVertex.add(data.getVertex(neighbor));
                    setData(9);
                    queue.add(neighbor);
                }
            }
        }

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
            frame.msg.setText("");
            if(isNew) {
                String s = frame.startVertexInput.getText();
                if(!s.equals(""))
                try {
                    int value = Integer.valueOf(s);
                    int startIndex = data.getIndex(value);
                    //input check
                    if(startIndex == -1) {
                        frame.msg.setText("vertex not valid!");
                        return;
                    }
                    if(previousGraph != null && data == previousGraph) {
                        data.visitedVertex.clear();
                        for(int i = 0; i < data.n; i++) {
                            for(int j = 0; j < data.n; j++ )
                                data.isPath[i][j] = false;
                            data.isVisited[i] = false;
                        }
                        setData(-1);
                    }

                    thread = new Thread(() -> {
                        setData(1);
                        bfs(startIndex);
                        setData(-1);
                        AlgoVisHelper.pause(1000);
                        isNew = true;
                        pause = false;
                        previousGraph = data;
                        frame.run.setText("Run");
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
            Collections.addAll(vertices, v1, v2, v3, v4, v5);

            data = new GraphData(vertices, false,"bfs", false);
            isNew = true;
            frame.run.setText("Run");
            pause = false;
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
