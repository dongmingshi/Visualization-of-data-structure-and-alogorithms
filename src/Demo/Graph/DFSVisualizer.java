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

public class DFSVisualizer {

    private int delay = 100;
    private GraphData data;
    private GraphFrame frame;
    private boolean pause;

    private Vertex v1 = new Vertex(260, 370, 1),
            v2 = new Vertex(320, 170, 2),
            v3 = new Vertex(210, 270, 3),
            v4 = new Vertex(380, 370, 4),
            v5 = new Vertex(430, 270, 5);

    public DFSVisualizer(int sceneWidth, int sceneHeight) {

        ArrayList<Vertex> vertices = new ArrayList<>();
        Collections.addAll(vertices, v1, v2, v3, v4, v5);

        data = new GraphData(vertices,false,"dfs", false);

        EventQueue.invokeLater(() -> {
            frame = new GraphFrame("Depth First Search", sceneWidth, sceneHeight);
            frame.run.addActionListener(new RunHandler());
            frame.newGraph.addActionListener(new NewGraphHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }


    public void dfs(int index) {
        setData(1);
        data.isVisited[index] = true;
        Vertex v = data.getVertex(index);
        data.visitedVertex.add(v);
        setData(2);
        ArrayList<Integer> neighbors = data.getNeighbors(index);
        for(int neighbor : neighbors) {
            setData(3);
            if(!data.isVisited[neighbor]) {
                setData(4);
                data.isPath[index][neighbor] = true;
                data.isPath[neighbor][index] = true;
                setData(5);
                dfs(neighbor);
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
                    if(startIndex == -1) {
                        frame.msg.setText("vertex not valid!");
                        return;
                    }
                        if (previousGraph != null && data == previousGraph) {
                            data.visitedVertex.clear();
                            for (int i = 0; i < data.n; i++) {
                                for (int j = 0; j < data.n; j++)
                                    data.isPath[i][j] = false;
                                data.isVisited[i] = false;
                            }
                            setData(-1);
                        }
                        thread = new Thread(() -> {
                            setData(1);
                            dfs(startIndex);
                            setData(-1);
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
                    synchronized (data) {
                        data.notify();
                    }
                    pause = false;
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

            data = new GraphData(vertices, false,"dfs", false);
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
