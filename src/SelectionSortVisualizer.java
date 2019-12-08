import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionSortVisualizer {

    private int delay = 20;

    private SelectionSortData data;
    private SelectionSortFrame frame;

    public SelectionSortVisualizer(int sceneWidth, int sceneHeight, int N){

        // 初始化数据
        data = new SelectionSortData(N, (sceneHeight - 100) / 10);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new SelectionSortFrame("Selection Sort", sceneWidth, sceneHeight);
            frame.start.addActionListener(new StartHandler());
            frame.random.addActionListener(new RandomizeHandler());
            frame.speed.addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void run(){

        setData(0, -1, -1, -1);

        for(int i = 0; i < data.N(); i++) {

            setData(i, -1, -1, 6);
            int min = i;
            setData(i, -1, min, 7);

            for(int j = i + 1; j < data.N(); j++) {
                setData(i, j, min, 8);
                if (data.get(min) > data.get(j)) {
                    setData(i, j, min, 9);
                    min = j;
                    setData(i, j, min, 10);
                }
            }
            data.swap(i, min);
            setData(i+1, -1, -1, 11);
        }
        setData(data.N(), -1, -1, -1);

    }

    private void setData(int orderedIndex, int curCompareIndex, int curMinIndex, int curLine) {
        data.orderedIndex = orderedIndex;
        data.curCompareIndex = curCompareIndex;
        data.curMinIndex = curMinIndex;
        data.curLine = curLine;

        frame.render(data);
        AlgoVisHelper.pause(delay);
    }

    private class StartHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread(() -> {
                frame.random.setEnabled(false);
                frame.start.setEnabled(false);
                run();
                frame.random.setEnabled(true);
                frame.start.setEnabled(true);
            }).start();
        }
    }

    private class RandomizeHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            data = new SelectionSortData(data.N(), (frame.getCanvasHeight() - 100) / 10);
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

