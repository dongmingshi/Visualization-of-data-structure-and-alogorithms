package Demo.MergeSort;

import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MergeSortVisualizer {

    private int delay;
    private boolean isNew;
    private boolean pause;
    private MergeSortFrame frame;
    private MergeSortData data;

    public MergeSortVisualizer(int sceneWidth, int sceneHeight, int N) {
        delay = 50;
        isNew = true;
        pause = false;
        data = new MergeSortData(N, (sceneHeight - 100) / 10);
        EventQueue.invokeLater(() -> {
            frame = new MergeSortFrame("Merge Sort", sceneWidth, sceneHeight);

            frame.getStart().addActionListener(new StartHandler());
            frame.getGenerate().addActionListener(new GenerateHandler());
            frame.getRandom().addActionListener(new RandomHandler());
            frame.getSpeed().addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    public void run() {
        setData(-1, -1, -1, -1);
        mergeSort(0, data.N() - 1);
        setData(0, data.N() - 1, data.N() - 1, -1);
    }

    public void mergeSort(int l, int r) {
        if(l >= r) {
            setData(l, r, -1, 3);
            return;
        }
        setData(l, r, -1, 4);
        int mid = (l+r) / 2;
        setData(l, r, -1, 5);
        mergeSort(l, mid);
        setData(l, r, -1, 6);
        mergeSort(mid+1, r);
        setData(l, r, -1, 7);
        merge(l, mid, r);
    }

    public void merge(int l, int m, int r) {
        setData(l, r, -1, 10);
        int[] arr = Arrays.copyOfRange(data.numbers, l, r+1);
        setData(l, r, -1, 11);
        int i = l, j = m + 1;
        for(int k = l; k <= r; k++) {
            setData(l, r, k-1, 12);
            if(i > m) {
                setData(l, r, k-1, 13);
                data.numbers[k] = arr[j - l];
                j++;
                setData(l, r, k, 14);
            } else if(j > r) {
                setData(l, r, k-1, 15);
                data.numbers[k] = arr[i - l];
                i++;
                setData(l, r, k, 16);
            } else if(arr[i - l] <= arr[j - l]) {
                setData(l, r, k-1, 17);
                data.numbers[k] = arr[i - l];
                i++;
                setData(l, r, k, 18);
            } else {
                setData(l, r, k-1, 19);
                data.numbers[k] = arr[j - l];
                j++;
                setData(l, r, k, 20);
            }
        }
    }

    public void setData(int l , int r, int mergeIndex, int curLine) {
        if(!pause) {
            data.l = l;
            data.r = r;
            data.mergeIndex = mergeIndex;
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
                    pause = false;
                    synchronized (data) {
                        data.notify();
                    }
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

                    data = new MergeSortData(quantity,
                            (frame.getCanvasHeight()-100) / 10);
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

            data = new MergeSortData((int)(Math.random() * 30) + 2,
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
