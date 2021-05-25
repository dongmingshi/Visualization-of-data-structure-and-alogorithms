package Demo.Graph;

import Demo.AlgoVisHelper;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphFrame extends JFrame {
    private int canvasWidth;
    private int canvasHeight;

    protected JLabel startVertex;
    protected JTextField startVertexInput;
    protected JButton run;
    protected JButton newGraph;
    protected JLabel delay;
    protected JSlider speed;
    protected JLabel msg;
    protected JPanel control;

    public GraphFrame(String title, int canvasWidth, int canvasHeight) {
        super(title);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        startVertex = new JLabel("Start Vertex: ");
        startVertexInput = new JTextField(5);
        run = new JButton("Run");
        newGraph = new JButton("New Graph");

        delay = new JLabel("    Delay(ms):");
        speed = new JSlider(20, 300, 100);
        speed.setPreferredSize(new Dimension(400, 40));
        speed.setMajorTickSpacing(50);
        speed.setMinorTickSpacing(10);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);

        msg = new JLabel("");
        control = new JPanel();

        control.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        control.add(startVertex);
        control.add(startVertexInput);
        control.add(run);
        control.add(newGraph);
        control.add(delay);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        control.add(speed, constraints);
        control.add(msg, constraints);

        AlgoCanvas canvas = new AlgoCanvas();
        canvas.add(control);
        setContentPane(canvas);

        setResizable(false);
        pack();

        setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - canvasWidth)/2),
                (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - canvasHeight)/2));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    GraphData data;
    public void render(GraphData data) {
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            //msg
            if(!data.visitedVertex.isEmpty()) {

                if(!data.isWeighted) {

                    String msg = "Visited Vertex: ";
                    for (Vertex vertex : data.visitedVertex)
                        msg += vertex.value + "\t";
                    AlgoVisHelper.drawText(g2d, msg, getCanvasWidth() / 3, 100);
                } else {
                    if(!data.visitedEdge.isEmpty()) {
                        String edgeMsg = "MST: ";
                        for (int[] edge : data.visitedEdge)
                            edgeMsg += "(" + edge[0] + "," + edge[1] + ")\t";
                        AlgoVisHelper.drawText(g2d, edgeMsg, getCanvasWidth() / 3, 100);
                        AlgoVisHelper.drawText(g2d, "Cost: " + data.cost, getCanvasWidth() / 3 + 10, getCanvasHeight() - 100);
                    }

//                    if(data.op.equals("prim")) {
//                        ArrayList<Vertex> vertices = new ArrayList<>();
//                        vertices.addAll(data.vertexList);
//                        vertices.removeAll(data.visitedVertex);
//                        if (!vertices.isEmpty()) {
//                            String[] titles = {"u", "v", "cost"};
//                            int w = 40, h = 20;
//                            int sx = 50, sy = 100;
//                            for (int i = 0; i < 3; i++) {
//                                AlgoVisHelper.strokeRectangle(g2d, sx + (w + 1) * i, sy, w, h);
//                                AlgoVisHelper.drawText(g2d, titles[i], sx + (w + 1) * i + w / 2,
//                                        sy + h / 2);
//                            }
//                            int row = 1;
//                            for (Vertex vertex : data.visitedVertex) {
//                                int index = data.getIndex(vertex.value);
//                                for (int neighbor : data.getNeighbors(index)) {
//                                    if(!data.isVisited[neighbor]) {
//                                        int u = data.getVertex(index).value;
//                                        int v = data.getVertex(neighbor).value;
//                                        if (data.umin == index && data.vmin == neighbor)
//                                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
//                                        else
//                                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
//                                        int[] e = {u, v, data.edges[index][neighbor]};
//                                        for (int i = 0; i < 3; i++) {
//                                            AlgoVisHelper.strokeRectangle(g2d, sx + (w + 1) * i, sy + (h + 1) * row, w, h);
//                                            AlgoVisHelper.drawText(g2d, String.valueOf(e[i]),
//                                                    sx + (w + 1) * i + w / 2, sy + (h + 1) * row + h / 2);
//                                        }
//                                        row++;
//                                    }
//                                }
//                            }
//                        }
//                    }


                }
            }

            //dijkstra
            if(data.op.equals("dijkstra") && ((SSSPData)data).end) {
                int sx = 50, sy = 100;
                g2d.drawString("Single Source Shortest Path: ", sx, sy);

                for(int i = 0; i < data.vertexList.size(); i++) {
                    String msg = data.getVertex(i).value + ": ";
                    ArrayList<Integer> path = ((SSSPData)data).getPath(i);
                    for(int v : path) {
                        int vertex  = data.getVertex(v).value;
                        msg +=  vertex + "->";
                    }
                    msg +=  data.getVertex(i).value;
                    FontMetrics metrics = g2d.getFontMetrics();
                    int h = metrics.getHeight();
                    g2d.drawString(msg, sx, sy + (h + 10) *(i + 1));
                }
            }

            //edges
            for (int i = 0; i < data.n; i++)
                for (int j = i + 1; j < data.n; j++)
                    if (data.edges[i][j] != 0) {
                        AlgoVisHelper.setStrokeWidth(g2d, 1);

                        Vertex v = data.getVertex(i);
                        Vertex w = data.getVertex(j);
                        if (data.isPath[i][j])
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                        else
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);

                        double D = Math.sqrt( (v.x - w.x) * (v.x - w.x) + (v.y - w.y) * (v.y - w.y) );
                        double dx = Math.ceil(v.r / D * (v.x - w.x));
                        double dy = Math.ceil(v.r / D * (v.y - w.y));
                        double x1 = v.x - dx, y1 = v.y - dy,
                                x2 = w.x + dx, y2 = w.y + dy;

                        if(!data.isWeighted)
                            AlgoVisHelper.drawLine(g2d, x1, y1, x2, y2);
                        else {
                            double mx = (v.x + w.x) / 2;
                            double my = (v.y + w.y) / 2;
                            if(v.value == 3 && w.value == 5)
                                mx -= 20;
                            if((v.value == 1 && w.value == 6) || (v.value == 2 && w.value == 4))
                                my += 15;
                            double dmx = Math.abs(20 / v.r * dx);
                            double dmy = Math.abs(20 / v.r * dy);
                            if (v.value == 1 && w.value == 2) {
                                mx += dmx;
                                my -= dmy;
                            }
                            if(v.value == 4 && w.value == 6) {
                                mx -= dmx;
                                my -= dmy;
                            }
                            if(v.value == 2 && w.value == 3) {
                                mx -= dmx;
                                my += dmy;
                            }

                            if(v.value == 3 && w.value == 4) {
                                mx -= dmx;
                                my -= dmy;
                            }

                            if(v.value == 1 && w.value == 5) {
                                mx += dmx;
                                my -= dmy;
                            }

                            if(v.value == 5 && w.value == 6) {
                                mx += dmx;
                                my += dmy;
                            }

                            FontMetrics font = g2d.getFontMetrics();
                            int fontHeight = font.getHeight();
                            int fontWidth = font.stringWidth(String.valueOf(data.edges[i][j]));
                            int fontD = (int) Math.sqrt(fontHeight * fontHeight + fontWidth * fontWidth);
                            double fdx = (fontD + 5) / v.r * dx;
                            double fdy = (fontD + 5) / v.r * dy;
                            double ex1 = mx + fdx / 2,
                                    ey1 = my + fdy / 2 ,
                                    sx2 = mx - fdx / 2,
                                    sy2 = my - fdy / 2;

                            AlgoVisHelper.drawText(g2d, String.valueOf(data.edges[i][j]),
                                    (int)mx, (int)my);
                            AlgoVisHelper.drawLine(g2d, (int)x1, (int)y1, (int)ex1, (int)ey1);
                            AlgoVisHelper.drawLine(g2d, (int)sx2, (int)sy2, (int)x2, (int)y2);
                        }
                    }

            //vertices
            for(Vertex v : data.vertexList) {
                int index = data.getIndex(v.value);
                if(data.isVisited[index]) {
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                    AlgoVisHelper.setStrokeWidth(g2d, 3);
                } else {
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Black);
                    AlgoVisHelper.setStrokeWidth(g2d, 1);
                }
                AlgoVisHelper.strokeCircle(g2d, v.x, v.y, v.r);
                AlgoVisHelper.drawText(g2d, String.valueOf(v.value), v.x, v.y);
            }

            code(g2d, "src/" + data.op);
            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);
            AlgoVisHelper.setStrokeWidth(g2d, 1);
            if(data.op.equals("dijkstra"))
                AlgoVisHelper.drawLine(g2d, 2 * canvasWidth / 3 - 120, 90,
                        2 * canvasWidth / 3 - 120, canvasHeight - 50);
            else
                AlgoVisHelper.drawLine(g2d, 2 * canvasWidth / 3 - 50, 90,
                    2 * canvasWidth / 3 - 50, canvasHeight - 50);
        }

        public void code(Graphics2D g, String filename) {
            int x, w;
            if(data.op.equals("dijkstra")) {
                x = 2 * canvasWidth / 3 - 100;
                w = canvasWidth / 3 + 90;
            } else {
                x = 2 * canvasWidth / 3;
                w = canvasWidth / 3 - 10;
            }
            AlgoVisHelper.drawTextFromFile(g, filename, data,
                    x, 150, w);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(getCanvasWidth(), getCanvasHeight());
        }
    }
}
