package Demo.Tree;

import Demo.AlgoVisHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AVLFrame extends TreeFrame {

    public AVLFrame(int canvasWidth, int canvasHeight) {
        super("AVL Tree", canvasWidth, canvasHeight);
        type = "avl";

        JPanel control = new JPanel();
        control.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        control.add(getInsertInput());
        control.add(getInsertBtn());
        control.add(getDeleteInput());
        control.add(getDeleteBtn());
        control.add(getClearBtn());
        control.add(getDelay());
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        control.add(getSpeed(), constraints);
        control.add(getMsg(), constraints);

        AlgoCanvas canvas = new AlgoCanvas();
        canvas.add(control);
        setContentPane(canvas);

        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private TreeData data;
    public void render(TreeData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            int r = 15;
            double degree = Math.PI / 3;
            int rootx = getCanvasWidth() / 3, rooty = 120;
            if(data.root != null) {
                ArrayList<Node> set = new ArrayList<>();
                AlgoVisHelper.drawTree(g2d, AVLFrame.this, set, data.root, rootx, rooty, r, degree);
            }
            if(data.op.equals("insert"))
                AlgoVisHelper.drawTextFromFile(g2d, "src/AVLInsert", data, 2*getCanvasWidth()/3, 100, getCanvasWidth()/3);
            if(data.op.equals("delete"))
                AlgoVisHelper.drawTextFromFile(g2d, "src/AVLDelete", data, 2*getCanvasWidth()/3, 100, getCanvasWidth()/3);
        }
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(getCanvasWidth(), getCanvasHeight());
        }
    }
}
