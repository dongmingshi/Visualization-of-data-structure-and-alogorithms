package Demo.MergeSort;

import Demo.AlgoVisHelper;
import Demo.SortFrame;

import javax.swing.*;
import java.awt.*;

public class MergeSortFrame extends SortFrame {

    public MergeSortFrame(String title, int canvasWidth, int canvasHeight) {
        super(title, canvasWidth, canvasHeight);

        AlgoCanvas canvas = new AlgoCanvas();
        canvas.add(quantity);
        canvas.add(inputQuantity);
        canvas.add(generate);
        canvas.add(random);
        canvas.add(start);
        canvas.add(delay);
        canvas.add(speed);
        canvas.add(msg);

        setContentPane(canvas);
        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private MergeSortData data;
    public void render(MergeSortData data) {
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
            //int w = (2 * getCanvasWidth() / 3) / (data.N() + 2);
            int w = 20;
            for(int i = 0; i < data.N(); i++) {
                if(i >= data.l && i <= data.r)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);
                if(i >= data.l && i <= data.mergeIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Blue);

                AlgoVisHelper.fillRectangle(g2d, getCanvasWidth()/3 - data.N()/2 * w + w*i, getCanvasHeight() - data.get(i)*10 - 20, w-1, data.get(i)*10);
                AlgoVisHelper.drawText(g2d, String.valueOf(data.get(i)), getCanvasWidth()/3 - data.N()/2 * w + w*i + w/2, getCanvasHeight() - data.get(i)*10 - 30);
            }

            AlgoVisHelper.drawTextFromFile(g2d, "src/MergeSort.txt", data, 2*getCanvasWidth()/3, 100, getCanvasWidth()/3);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(getCanvasWidth(), getCanvasHeight());
        }
    }

}
