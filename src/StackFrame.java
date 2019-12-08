import javax.swing.*;
import java.awt.*;

public class StackFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    JTextField push = new JTextField(5);
    JButton pushBtn  =  new JButton("Push");
    JButton popBtn = new JButton("Pop");
    JButton clearBtn = new JButton("Clear Stack");
    JLabel popValue = new JLabel();

    public StackFrame(String title, int canvasWidth, int canvasHeight) {
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        StackCanvas canvas = new StackCanvas();
        canvas.add(push);
        canvas.add(pushBtn);
        canvas.add(popBtn);
        canvas.add(clearBtn);
        canvas.add(popValue);
        setContentPane(canvas);

        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private StackData data;
    public void render(StackData data){
        this.data = data;
        repaint();
    }

    private class StackCanvas extends JPanel {
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
                if(i <= data.top)
                    AlgoVisHelper.drawText(g2d, data.get(i).toString(), canvasWidth/2, canvasHeight - h*(i+2) + h/2);
                if(i == data.top)
                    AlgoVisHelper.setColor(g2d,AlgoVisHelper.Red);
                else
                    AlgoVisHelper.setColor(g2d,AlgoVisHelper.Black);
                AlgoVisHelper.strokeRectangle(g2d, (canvasWidth - w)/2, canvasHeight - h*(i+2), w, h);
            }

            AlgoVisHelper.drawText(g2d, "Top: " + data.top, 3*canvasWidth/4, 100 );
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
