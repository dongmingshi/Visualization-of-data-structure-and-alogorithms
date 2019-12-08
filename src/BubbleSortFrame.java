import javax.swing.*;
import java.awt.*;

public class BubbleSortFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    JButton random = new JButton("Randomize data");
    JButton start = new JButton("Play");
    JSlider speed = new JSlider(20, 300, 50);
    JLabel delay = new JLabel("    Delay(ms):");

    public BubbleSortFrame(String title, int canvasWidth, int canvasHeight) {

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        canvas.add(random);
        canvas.add(start);
        canvas.add(delay);
        canvas.add(speed);
        speed.setPreferredSize(new Dimension(400, 40));
        speed.setMajorTickSpacing(50);
        speed.setMinorTickSpacing(10);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
        setContentPane(canvas);


        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private BubbleSortData data;
    public void render(BubbleSortData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            //draw
            int w = (2 * canvasWidth / 3) / (data.N() + 2);
            for(int i = 0; i <data.N(); i++ ) {
                if(i >= data.orderedIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);

                if((i == data.curIndex && data.curIndex != data.N()-1) || (i == data.curIndex + 1 && data.curIndex != -1))
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);

                AlgoVisHelper.drawText(g2d, String.valueOf(data.get(i)), (i+1) * w + w/2,canvasHeight - 10);
                AlgoVisHelper.fillRectangle(g2d, (i+1) * w, canvasHeight - data.get(i)*10 - 20, w-1, data.get(i)*10);

            }

            AlgoVisHelper.drawTextFromFile(g2d, "BubbleSort.txt", data, 2*canvasWidth/3, 100, canvasWidth/3);

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
