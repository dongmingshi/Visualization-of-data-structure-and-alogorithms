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

        QueueCanvas canvas = new QueueCanvas();
        canvas.add(enqueue);
        canvas.add(enqueueBtn);
        canvas.add(dequeueBtn);
        canvas.add(clearBtn);
        canvas.add(dequeueValue);
        setContentPane(canvas);

        setResizable(false);
        pack();

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
            for(int i = 0; i < data.N(); i++) {
                AlgoVisHelper.strokeRectangle(g2d, (canvasWidth - w)/2, 70 + h*i, w, h);
                if(data.front != data.rear && i >= data.front && i < data.rear)
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
