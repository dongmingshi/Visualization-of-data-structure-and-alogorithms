import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueVisualizer {
    private static int h = 25;

    private QueueFrame frame;
    private QueueData data;

    QueueVisualizer(int N) {

        data = new QueueData(N);

        int sceneWidth = 500;
        int sceneHeight = h*data.N() + 100;
        EventQueue.invokeLater(() -> {
            frame = new QueueFrame( sceneWidth, sceneHeight);
            frame.enqueueBtn.addActionListener(new enqueueHandler());
            frame.dequeueBtn.addActionListener(new dequeueHandler());
            frame.clearBtn.addActionListener(new clearHandler());
            frame.render(data);
            //new Thread(() -> {
            // run();
            //}).start();
        });
    }

    private class enqueueHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = frame.enqueue.getText();
            if(!text.equals("") && data.rear <= data.N()) {
                data.enqueue(text);
                frame.enqueue.setText("");
                frame.render(data);
            }
        }
    }

    private class dequeueHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(data.front < data.rear) {
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
            frame.render(data);
        }
    }
}
