package Demo.InsertionSort;

import Demo.AlgoVisHelper;
import Demo.SortFrame;

import javax.swing.*;
import java.awt.*;


public class InsertionSortFrame extends SortFrame {
    private int canvasWidth;
    private int canvasHeight;

    public InsertionSortFrame(String title, int canvasWidth, int canvasHeight) {

        super(title, canvasWidth, canvasHeight);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        canvas.add(quantity);
        canvas.add(inputQuantity);
        canvas.add(generate);
        canvas.add(random);
        canvas.add(start);
        //canvas.add(getPause());
        canvas.add(delay);
        canvas.add(speed);
        setContentPane(canvas);

        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private InsertionSortData data;
    public void render(InsertionSortData data){
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
            int w = 20;
            for(int i = 0; i < data.N(); i++) {

                if(i < data.orderedIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);

                if(i == data.curIndex) {
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                    AlgoVisHelper.drawText(g2d, "current", getCanvasWidth()/3 -  w * data.N() /2 + i * w + w/2,canvasHeight - 10);
                }

                AlgoVisHelper.drawText(g2d, String.valueOf(data.get(i)), getCanvasWidth()/3 -  w * data.N() /2 + i * w + w/2,canvasHeight - data.get(i)*10 - 30);
                AlgoVisHelper.fillRectangle(g2d, getCanvasWidth()/3 -  w * data.N() /2 + i * w, canvasHeight - data.get(i)*10 - 20, w-1, data.get(i)*10);
            }

            AlgoVisHelper.drawTextFromFile(g2d, "src/InsertionSort.txt", data, 2*canvasWidth/3, 100, canvasWidth/3);

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }

}
