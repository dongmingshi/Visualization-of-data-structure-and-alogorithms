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

public class DijkstraVisualizer {

    private static int delay = 100;
    private SSSPData data;
    private GraphFrame frame;
    private boolean pause;

    private Vertex v1 = new Vertex(300, 370, 1),
            v2 = new Vertex(400, 170, 2),
            v3 = new Vertex(210, 270, 3),
            v4 = new Vertex(400, 370, 4),
            v5 = new Vertex(490, 270, 5),
            v6 = new Vertex(300, 170, 6);

    public DijkstraVisualizer(int sceneWidth, int sceneHeight) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        Collections.addAll(vertices, v1, v2, v3, v4, v5, v6);

        data = new SSSPData(vertices, false,"dijkstra", true);
        EventQueue.invokeLater(() -> {
            frame = new GraphFrame("Dijkstra", sceneWidth, sceneHeight);
            frame.run.addActionListener(new RunHandler());
            frame.newGraph.addActionListener(new NewGraphHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void dijkstra(int source) {
        int sourceIndex = data.getIndex(source);
        setData(2);
        data.distance[sourceIndex] = 0;
        data.parent[sourceIndex] = sourceIndex;

        LinkedList<Distance> queue = new LinkedList<>();
        setData(3);
        queue.add( new Distance(sourceIndex, 0) );

        while(!queue.isEmpty()) {
            setData(4);
            Collections.sort(queue);
            //System.out.println(queue);
            setData(5);
            int min = queue.removeFirst().vertex;
            data.isVisited[min] = true;
            data.isPath[min][data.parent[min]] = true;
            data.isPath[data.parent[min]][min] = true;
            ArrayList<Integer> neighbors = data.getNeighbors(min);
            setData(6);
            for(int neighbor : neighbors) {
                if(!data.isVisited[neighbor] &&
                        data.distance[min] + data.edges[min][neighbor]< data.distance[neighbor]) {
                    setData(7);
                    data.parent[neighbor] = min;
                    setData(8);
                    data.distance[neighbor] = data.distance[min] + data.edges[min][neighbor];
                    setData(9);
                    boolean exist = false;
                    setData(10);
                    for(Distance d : queue)
                        if(d.vertex == neighbor) {
                            exist = true;
                            d.cost = data.distance[neighbor];
                        }
                    setData(11);
                    if(!exist)
                        queue.add( new Distance(neighbor, data.distance[neighbor]) );

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

    SSSPData previousGraph;
    Thread thread;
    boolean isNew = true;
    private class RunHandler implements ActionListener{

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
                    data.source = value;
                    if (previousGraph != null && data == previousGraph) {
                        data.end = false;
                        for (int i = 0; i < data.n; i++) {
                            for (int j = 0; j < data.n; j++)
                                data.isPath[i][j] = false;
                            data.isVisited[i] = false;
                            data.parent[i] = i;
                            data.distance[i] = Integer.MAX_VALUE;
                        }
                        setData(-1);
                    }

                    thread = new Thread(() -> {
                        setData(1);
                        dijkstra(value);
                        data.end = true;
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

            data = new SSSPData(vertices, false,"dijkstra", true);
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

class Distance implements Comparable{
    int vertex;
    int cost;

    public Distance(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) {
        return this.cost - ((Distance) o).cost;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "vertex=" + vertex +
                ", cost=" + cost +
                '}';
    }
}
