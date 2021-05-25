package Demo.Stack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StackVisualizer {

    private static int DELAY = 20;
    private static int h = 25;

    private StackData data;
    private StackFrame frame;

    public StackVisualizer(int N) {

        data = new StackData(N);

        int sceneWidth = 500;
        int sceneHeight = h*data.N() + 100;

        EventQueue.invokeLater(() -> {
            frame = new StackFrame("Stack Visualization", sceneWidth, sceneHeight);
            frame.pushBtn.addActionListener(new pushHandler());
            frame.popBtn.addActionListener(new popHandler());
            frame.clearBtn.addActionListener(new clearHandler());
            frame.render(data);
        });
    }

    private class pushHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.popValue.setText("");
            String text = frame.push.getText();
            try{
                int val = Integer.valueOf(text);
                if(val > frame.capacity) {
                    frame.popValue.setText("No more than " + frame.capacity);
                    return;
                }
                if(val < frame.capacity * (-1)) {
                    frame.popValue.setText("No less than " + frame.capacity * (-1));
                    return;
                }
                if( data.top < data.N()) {
                    data.push(val);
                    frame.push.setText("");
                    frame.render(data);
                }
            } catch (Exception exp) {
                frame.popValue.setText("Integer Only");
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
            data.top = -1;
            frame.popValue.setText("");
            frame.render(data);

        }
    }
}
