package Demo.Queue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueVisualizer {
    private static int h = 25;

    private QueueFrame frame;
    private QueueData data;

    public QueueVisualizer(int N) {

        data = new QueueData(N);

        int sceneWidth = 500;
        int sceneHeight = h*data.N() + 100;
        EventQueue.invokeLater(() -> {
            frame = new QueueFrame( sceneWidth, sceneHeight);
            frame.enqueueBtn.addActionListener(new enqueueHandler());
            frame.dequeueBtn.addActionListener(new dequeueHandler());
            frame.clearBtn.addActionListener(new clearHandler());
            frame.render(data);
        });
    }

    private class enqueueHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dequeueValue.setText("");
            String text = frame.enqueue.getText();
            try {
                int value = Integer.valueOf(text);
                if(value > frame.capacity) {
                    frame.dequeueValue.setText("No more than " + frame.capacity);
                    return;
                }
                if(value < frame.capacity * (-1)) {
                    frame.dequeueValue.setText("No less than " + frame.capacity * (-1));
                    return;
                }
                if(data.total < data.N()) {
                    data.enqueue(value);
                    frame.enqueue.setText("");
                    frame.render(data);
                }
            } catch (Exception exp) {
                frame.dequeueValue.setText("Integer Only");
            }
        }
    }

    private class dequeueHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(data.total > 0) {
                Object value = data.dequeue();
                frame.dequeueValue.setText("Dequeue value: " + value.toString());
                frame.render(data);
            }
        }
    }

    private class clearHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            data.front = 0;
            data.rear = 0;
            data.total = 0;
            frame.dequeueValue.setText("");
            frame.render(data);
        }
    }
}
