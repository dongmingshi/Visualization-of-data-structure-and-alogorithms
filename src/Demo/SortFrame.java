package Demo;
import javax.swing.*;
import java.awt.*;

public class SortFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    protected JLabel quantity;
    protected JTextField inputQuantity;
    protected JButton generate;
    protected JButton random;
    protected JButton start;
    protected JSlider speed;
    protected JLabel delay;
    protected JLabel msg;

    public SortFrame(String title, int canvasWidth, int canvasHeight) {

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        quantity = new JLabel("Quantity (max 32)");
        inputQuantity = new JTextField(3);
        generate = new JButton("Generate data");
        random = new JButton("Randomize data");

        start = new JButton("Play");
        //pause = new JButton("Pause");

        speed = new JSlider(20, 300, 50);
        delay = new JLabel("    Delay(ms):");

        msg = new JLabel("");

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

    public JButton getGenerate() {
        return generate;
    }

    public JButton getRandom() {return random;}

    public JButton getStart() {
        return start;
    }

    public JSlider getSpeed() {
        return speed;
    }

    public JLabel getDelay() {
        return delay;
    }

    public JTextField getInputQuantity() {return inputQuantity;}

    public JLabel getMsg() {return msg;}
}
