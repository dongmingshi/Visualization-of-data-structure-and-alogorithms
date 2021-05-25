package Demo.InsertionSort;

import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertionSortVisualizer {

    private int delay = 50;
    private boolean pause = false;
    private boolean isNew = true;

    private InsertionSortData data;
    private InsertionSortFrame frame;

    public InsertionSortVisualizer(int sceneWidth, int sceneHeight, int N) {

        data = new InsertionSortData(N, (sceneHeight - 100) / 10);

        EventQueue.invokeLater(() -> {
            frame = new InsertionSortFrame("Insertion Sort Visualization", sceneWidth, sceneHeight);
            frame.getStart().addActionListener(new StartHandler());
            frame.getGenerate().addActionListener(new GenerateHandler());
            frame.getRandom().addActionListener(new RandomHandler());
            frame.getSpeed().addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void run() {

        for(int i = 0; i < data.N(); i++) {
            setData(i, i, 6);
            for(int j = i; j > 0 && data.get(j) < data.get(j-1); j--) {
                setData(i+1, j, 7);
                data.swap(j, j-1);
                setData(i+1, j-1, 8);
            }
        }
        setData(data.N(), -1, -1);
    }

    private void setData(int orderedIndex, int curIndex, int curLine) {
        if(!pause) {
            data.orderedIndex = orderedIndex;
            data.curIndex = curIndex;
            data.curLine = curLine;

            frame.render(data);
            AlgoVisHelper.pause(delay);
        } else {
            synchronized (data) {
                try {
                    data.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    Thread thread;
    private class StartHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isNew) {
                thread = new Thread(() -> {
                    run();
                    frame.getStart().setText("Play");
                });
                thread.start();
                isNew = false;
                frame.getStart().setText("Pause");
            } else {
                if(frame.getStart().getText().equals("Play")) {
                    if(!thread.isAlive())
                        return;
                    synchronized (data) {
                        data.notify();
                    }
                    pause = false;
                    frame.getStart().setText("Pause");
                } else {
                    pause = true;
                    frame.getStart().setText("Play");
                }
            }

        }
    }

    private class GenerateHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String quantityStr = frame.getInputQuantity().getText();
            int quantity = AlgoVisHelper.validQuantity(frame, quantityStr);

            if(quantity != 0) {

                if(thread != null && thread.isAlive())
                    thread.stop();

                data = new InsertionSortData(quantity, (frame.getCanvasHeight() - 100) / 10);
                isNew = true;
                frame.getStart().setText("Play");
                pause = false;
                frame.render(data);
            }
        }
    }

    private class RandomHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(thread != null && thread.isAlive())
                thread.stop();

            data = new InsertionSortData((int)(Math.random() * 30) + 2,
                    (frame.getCanvasHeight() - 100) / 10);
            isNew = true;
            pause = false;
            frame.getStart().setText("Play");
            frame.render(data);
        }
    }

    private class SpeedHandler implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if((JSlider) e.getSource() == frame.getSpeed())
                delay = frame.getSpeed().getValue();
        }
    }
}
