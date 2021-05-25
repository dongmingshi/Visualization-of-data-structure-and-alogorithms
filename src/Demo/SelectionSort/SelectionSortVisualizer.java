package Demo.SelectionSort;

import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionSortVisualizer{

    private int delay = 50;
    private boolean pause = false;
    private boolean isNew = true;

    private SelectionSortData data;
    private SelectionSortFrame frame;

    public SelectionSortVisualizer(int sceneWidth, int sceneHeight, int N){

        // 初始化数据
        data = new SelectionSortData(N, (sceneHeight - 100) / 10);
        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new SelectionSortFrame("Selection Sort", sceneWidth, sceneHeight);

            frame.getStart().addActionListener(new StartHandler());
            frame.getGenerate().addActionListener(new GenerateHandler());
            frame.getRandom().addActionListener(new RandomHandler());
            frame.getSpeed().addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void run(){


            setData(0, -1, -1, -1);

            for (int i = 0; i < data.N(); i++) {

                setData(i, -1, -1, 6);
                int min = i;
                setData(i, -1, min, 7);

                for (int j = i + 1; j < data.N(); j++) {
                    setData(i, j, min, 8);
                    if (data.get(min) > data.get(j)) {
                        setData(i, j, min, 9);
                        min = j;
                        setData(i, j, min, 10);
                    }
                }
                data.swap(i, min);
                setData(i + 1, -1, -1, 11);
            }
            setData(data.N(), -1, -1, -1);

    }

    private void setData(int orderedIndex, int curCompareIndex, int curMinIndex, int curLine) {
        if(!pause) {
            data.orderedIndex = orderedIndex;
            data.curCompareIndex = curCompareIndex;
            data.curMinIndex = curMinIndex;
            data.curLine = curLine;

            frame.render(data);
            AlgoVisHelper.pause(delay);
        } else {
            synchronized (data){
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
                pause = false;
                thread = new Thread(()->{
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
                    frame.getStart().setText("Pause");
                    synchronized (data) {
                        data.notify();
                    }
                    pause = false;
                } else {
                    frame.getStart().setText("Play");
                    pause = true;
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

                data = new SelectionSortData(quantity, (frame.getCanvasHeight() - 100) / 10);
                isNew = true;
                frame.getStart().setText("Play");
                frame.render(data);
            }

        }

    }

    private class RandomHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(thread != null && thread.isAlive())
                thread.stop();

            data = new SelectionSortData((int)(Math.random() * 30) + 2,
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

