package Demo.Tree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TreeFrame extends JFrame {

    public String type;
    private int canvasWidth;
    private int canvasHeight;

    private JTextField insertInput;
    private JButton insertBtn;
    private JTextField deleteInput;
    private JButton deleteBtn;
    private JTextField searchInput;
    private JButton searchBtn;
    private JButton clearBtn;
    private JLabel msg;

    private JLabel delay;
    private JSlider speed;

    public TreeFrame(String title, int canvasWidth, int canvasHeight) {
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        insertInput = new JTextField(5);
        insertBtn = new JButton("Insert");
        deleteInput = new JTextField(5);
        deleteBtn = new JButton("Delete");
        searchInput = new JTextField(5);
        searchBtn = new JButton("Search");
        clearBtn = new JButton("Clear");
        msg = new JLabel("");

        delay = new JLabel("  Delay(ms):");
        speed = new JSlider(50, 500, 100);

        speed.setPreferredSize(new Dimension(300, 40));
        speed.setMajorTickSpacing(50);
        speed.setMinorTickSpacing(10);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);

        setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - canvasWidth)/2),
                (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - canvasHeight)/2));
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public JTextField getInsertInput() {
        return insertInput;
    }

    public JButton getInsertBtn() {
        return insertBtn;
    }

    public JTextField getDeleteInput() {
        return deleteInput;
    }

    public JButton getDeleteBtn() {
        return deleteBtn;
    }

    public JTextField getSearchInput() {
        return searchInput;
    }

    public JButton getSearchBtn() {
        return searchBtn;
    }

    public JLabel getDelay() {
        return delay;
    }

    public JSlider getSpeed() {
        return speed;
    }

    public JLabel getMsg() {
        return msg;
    }

    public JButton getClearBtn() {return clearBtn;}
}
