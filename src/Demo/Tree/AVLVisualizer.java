package Demo.Tree;

import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AVLVisualizer {
    private int delay = 100;
    private AVLFrame frame;
    private TreeData data;

    public AVLVisualizer(int sceneWidth, int sceneHeight) {
        data = new TreeData();
        EventQueue.invokeLater(() -> {
            frame = new AVLFrame(sceneWidth, sceneHeight);
            frame.getInsertBtn().addActionListener(new insertHandler());
            frame.getDeleteBtn().addActionListener(new deleteHandler());
            frame.getSpeed().addChangeListener(new SpeedHandler());
            frame.getClearBtn().addActionListener(new ClearHandler());
            frame.render(data);
        });
    }

    public void insert (Node node, int val) {
        Node newNode = new Node(val); //current finding position need render
        setData(node, true, false, true, 1);
        if(node.value == val)
            frame.getMsg().setText("Already exist!");
        else if (node.value > val) {
            setData(node, true, false, true, 2);
            if(node.left == null) {
                setData(node, true, false, true, 3);
                node.left = newNode; //insertion finish
                setData(node, false, false, true, 4);
            } else {
                setData(node, false, false, true, 6);
                insert(node.left, val);
            }
        } else {
            setData(node, true, false, true, 7);
            if(node.right == null) {
                setData(node, true, false, true, 8);
                node.right = newNode; //
                setData(node, false, false, true, 9);
            } else {
                setData(node, false, false, true, 11);
                insert(node.right, val);
            }
        }
        balance(node);
    }

    public void insertInTree(int val) {
        if(data.root == null) //
            data.root = new Node(val);
        else
            insert(data.root, val);
    }

    public Node search(Node node, int val) {
        setData(node, true, false,true, 2);
        if(node.value == val) {
            setData(node, false, false, true, 2);
            return node; //found
        }
        if(val < node.value && node.left != null) { //searching
            setData(node, false, false, true, 2);
            return search(node.left, val);
        }
        if(val > node.value && node.right != null) {
            setData(node, false, false, true, 2);
            return search(node.right, val);
        }
        setData(node, false, false, true, 2);
        return null;
    }

    public Node searchInTree(int val) {
        if(data.root != null) {
            Node target = search(data.root, val);
            if(target == null) {
                frame.getMsg().setText("Not found!");
                return null;
            } else {
                return target;
            }
        } else {
            return null;
        }
    }

    public void delete(int val) {
        //1. case1: empty tree
        if(data.root == null)
            return;
        //regular process: 1.search for target: exist or not
        Node target = searchInTree(val);
        if(target == null)
            return;
        setData(target, false, true, true, 3);
        Node parent = searchParentInTree(val);
        //left right all null
        if(target.left == null && target.right == null) {
            setData(target, false, true, true, 4);
            if(target == data.root) {
                data.root = null;
                rerender(5);
                return;
            }
            if(parent.left != null && parent.left == target) {
                parent.left = null;
                rerender(5);
                rerender(6);
                balance(parent);
                return;
            }
            if(parent.right != null && parent.right == target) {
                parent.right = null;
                rerender(5);
                rerender(6);
                balance(parent);
                return;
            }
        } else if(target.left != null && target.right == null) {
            setData(target, false, true, true, 10);
            if(target == data.root) {
                data.root = target.left;
                rerender(11);
                rerender(12);
                balance(data.root);
                return;
            }
            if(parent.left != null && parent.left == target) {
                parent.left = target.left;
                rerender(11);
                rerender(12);
                balance(parent);
                return;
            }
            if(parent.right != null && parent.right == target) {
                parent.right = target.left;
                balance(parent);
                return;
            }
        } else if(target.right != null && target.left == null) {
            setData(target, false, true, true, 10);
            if(target == data.root) {
                data.root = target.right;
                rerender(11);
                rerender(12);
                balance(data.root);
                return;
            }
            if(parent.left != null && parent.left == target) {
                parent.left = target.right;
                rerender(11);
                rerender(12);
                balance(parent);
                return;
            }
            if(parent.right != null && parent.right == target) {
                parent.right = target.right;
                rerender(11);
                rerender(12);
                balance(parent);
                return;
            }
        } else {
            setData(target, false, true, true, 7);
            int rightMinVal = rightMin(target);
            Node rightMinParent = searchParentInTree(rightMinVal);
            delete(rightMinVal);
            setData(target, false, true, true, 8);
            target.value = rightMinVal;
            setData(target, false, false, true, 8);
            rerender(9);
            balance(rightMinParent);
        }

    }

    public Node searchParent(Node node, int val) {
        if((node.left != null && node.left.value == val) || (node.right != null && node.right.value == val))
            return node;
        if(val < node.value && node.left != null)
            return searchParent(node.left, val);
        if(val > node.value && node.right != null)
            return searchParent(node.right, val);
        return null;
    }

    public Node searchParentInTree(int val) {
        if(data.root.value == val)
            return null;
        return searchParent(data.root, val);
    }

    public int rightMin(Node node) {
        Node right =  node.right;
        while(right.left != null)
            right = right.left;
        return right.value;
    }

    public void balance(Node node) {
        //左旋
        if(data.op.equals("insert"))
            setData(node, false, false, true, 12);
        if(node.rightHeight() - node.leftHeight() > 1) {
            if(data.op.equals("insert"))
                setData(node, false, false, false, 13);
            else
                setData(node, false, false, false);
            if(node.right.leftHeight() > node.right.rightHeight()) {
                if(data.op.equals("insert"))
                    setData(node, false, false, false, 14);
                node.right.rightRotate();
            }
            if(data.op.equals("insert"))
                setData(node, false, false, false, 15);
            else
                setData(node, false, false, false);
            node.leftRotate();
            if(data.op.equals("insert"))
                setData(node, false, false, true, 15);
            else
                setData(node, false, false, true);
            return;
        }
        //右
        if(data.op.equals("insert"))
            setData(node, false, false, true, 16);
        if(node.leftHeight() - node.rightHeight() > 1) {
            if(data.op.equals("insert"))
                setData(node, false, false, false, 17);
            else
                setData(node, false, false, false);
            if(node.left.rightHeight() > node.left.leftHeight()) {
                if(data.op.equals("insert"))
                    setData(node, false, false, false, 18);
                node.left.leftRotate();
            }
            if(data.op.equals("insert"))
                setData(node, false, false, false, 19);
            else
                setData(node, false, false, false);
            node.rightRotate();
            if(data.op.equals("insert"))
                setData(node, false, false, true, 19);
            else
                setData(node, false, false, true);
        }
    }

    private void setData(Node node, boolean isCur, boolean isTarget, boolean isBalance, int curLine) {
        node.cur = isCur;
        node.searched = isTarget;
        node.isBalance = isBalance;
        data.curLine = curLine;
        frame.render(data);
        AlgoVisHelper.pause(delay);
    }
    private void setData(Node node, boolean isCur, boolean isTarget, boolean isBalance) {
        node.cur = isCur;
        node.searched = isTarget;
        node.isBalance = isBalance;
        frame.render(data);
        AlgoVisHelper.pause(delay);
    }

    private void rerender(int curLine) {
        data.curLine = curLine;
        frame.render(data);
        AlgoVisHelper.pause(delay);
    }


    Thread thread;
    private class insertHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(thread != null && thread.isAlive())
                thread.stop();

            data.op="insert";
            frame.getMsg().setText("");
            String insertVal = frame.getInsertInput().getText();
            if(!insertVal.equals("") && insertVal != null) {
                try {
                    int val = Integer.valueOf(insertVal);
                    if(val >= 1000) {
                        frame.getMsg().setText("max 3 digits!");
                        return;
                    }
                    thread = new Thread(() -> {
                        insertInTree(val);
                        rerender(-1);
                    });
                    thread.start();
                } catch (Exception exp) {
                    frame.getInsertInput().setText("");
                    frame.getMsg().setText("Input integer only!");
                }
                frame.getInsertInput().setText("");
            }
        }
    }

    private class deleteHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(thread != null && thread.isAlive())
                thread.stop();

            data.op="delete";
            frame.getMsg().setText("");
            String deleteVal = frame.getDeleteInput().getText();
            if(!deleteVal.equals("") && deleteVal != null) {
                try {
                    int val = Integer.valueOf(deleteVal);
                    thread = new Thread(() -> {
                        delete(val);
                        rerender(-1);
                    });
                    thread.start();
                } catch (Exception exp) {
                    frame.getDeleteInput().setText("");
                    frame.getMsg().setText("Input integer only!");
                }
                frame.getDeleteInput().setText("");
            }
        }
    }

    private class ClearHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            data.op = "";
            data.root = null;
            frame.getMsg().setText("");
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
