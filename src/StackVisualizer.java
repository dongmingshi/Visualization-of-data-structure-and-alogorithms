import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StackVisualizer {

    private static int DELAY = 20;
    private static int h = 25;

    private StackData data;
    private StackFrame frame;

    StackVisualizer(int N) {

        data = new StackData(N);

        int sceneWidth = 500;
        int sceneHeight = h*data.N() + 100;

        EventQueue.invokeLater(() -> {
            frame = new StackFrame("Stack Visualization", sceneWidth, sceneHeight);
            frame.pushBtn.addActionListener(new pushHandler());
            frame.popBtn.addActionListener(new popHandler());
            frame.clearBtn.addActionListener(new clearHandler());
            frame.render(data);
            //new Thread(() -> {
               // run();
            //}).start();
        });
    }

    private class pushHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = frame.push.getText();
            if(!text.equals("") && data.top < data.N()) {
                data.push(text);
                frame.push.setText("");
                frame.render(data);
            }
        }
    }

    private class popHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(data.top > -1) {
                Object value = data.pop();
                frame.render(data);
                frame.popValue.setText("Popped value: " + value.toString());
            }
        }
    }

    private class clearHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(data.top > -1) {
                data.top = -1;
                frame.render(data);
            }
        }
    }
}
