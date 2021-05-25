package Demo.Queue;

import Demo.AlgoVisHelper;

import javax.swing.*;
import java.awt.*;

public class QueueFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    JTextField enqueue = new JTextField(5);
    JButton enqueueBtn = new JButton("Enqueue");
    JButton dequeueBtn = new JButton("Dequeue");
    JButton clearBtn = new JButton("Clear queue");
    JLabel dequeueValue = new JLabel();

    QueueFrame(int canvasWidth, int canvasHeight) {
        super("Queue Visualization");

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        JPanel control = new JPanel();
        control.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        control.add(enqueue);
        control.add(enqueueBtn);
        control.add(dequeueBtn);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        control.add(clearBtn, constraints);
        control.add(dequeueValue, constraints);

        QueueCanvas canvas = new QueueCanvas();
        canvas.add(control);
        setContentPane(canvas);

        setResizable(false);
        pack();

        setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - canvasWidth)/2),
                (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - canvasHeight)/2));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public int getCanvasWidth() {return canvasWidth;}
    public int getCanvasHeight() {return canvasHeight;}

    QueueData data;
    public void render(QueueData data){
        this.data = data;
        repaint();
    }

    public int capacity;

    private class QueueCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            int w = 70;
            int h = 25;

            FontMetrics metrics = g2d.getFontMetrics();
            capacity =  (int) Math.pow(10, (w - 10) / metrics.stringWidth("5"));

            for(int i = 0; i < data.N(); i++) {
                AlgoVisHelper.strokeRectangle(g2d, (canvasWidth - w)/2, 70 + h*i, w, h);
                if(data.front < data.rear && i >= data.front && i < data.rear || data.total > 0 && data.front >= data.rear && (i >= data.front || i < data.rear))
                    AlgoVisHelper.drawText(g2d, data.get(i).toString(), canvasWidth/2, 70 + h*i + h/2);
            }
            AlgoVisHelper.drawText(g2d, "Front: " + data.front, 3*canvasWidth/4, 100 );
            AlgoVisHelper.drawText(g2d, "Rear: " + data.rear, 3*canvasWidth/4, 120);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
