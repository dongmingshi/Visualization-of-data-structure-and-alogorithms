import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BubbleSortVisualizer {
    private int delay = 20;

    private BubbleSortData data;
    private BubbleSortFrame frame;
    public BubbleSortVisualizer(int sceneWidth, int sceneHeight, int N) {
        data = new BubbleSortData(N, (sceneHeight - 100) / 10);

        EventQueue.invokeLater(() -> {
            frame = new BubbleSortFrame("Bubble Sort Visualization", sceneWidth, sceneHeight);
            frame.start.addActionListener(new StartHandler());
            frame.random.addActionListener(new RandomizeHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void run() {
        frame.random.setEnabled(false);
        frame.start.setEnabled(false);

        for(int i = 0; i < data.N() - 1; i++) {
            setData(data.N() - i, -1, 7);
            for(int j = 0; j < data.N() - 1 - i; j++){
                setData(data.N() - i, j, 8);
                if(data.get(j) > data.get(j+1)) {
                    setData(data.N() - i, j, 9);
                    data.swap(j, j+1);
                    setData(data.N() - i, j, 10);
                }
            }
        }
        setData(data.N(), -1, -1);
        frame.random.setEnabled(true);
        frame.start.setEnabled(true);
    }

    private void setData(int orderedIndex, int curIndex, int curLine) {
        data.orderedIndex = orderedIndex;
        data.curIndex = curIndex;
        data.curLine = curLine;

        frame.render(data);
        AlgoVisHelper.pause(delay);
    }

    private class StartHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                run();
            }).start();
        }
    }

    private class RandomizeHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            data = new BubbleSortData(data.N(), (frame.getCanvasHeight() - 100) / 10);
            frame.render(data);
        }
    }

    private class SpeedHandler implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if((JSlider) e.getSource() == frame.speed)
                delay = frame.speed.getValue();
        }
    }
}
