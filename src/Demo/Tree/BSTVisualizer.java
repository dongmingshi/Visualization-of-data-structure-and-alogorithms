package Demo.Tree;

import Demo.AlgoVisHelper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BSTVisualizer {
    private int delay = 50;
    private TreeData data;
    private BSTFrame frame;

    public BSTVisualizer(int sceneWidth, int sceneHeight) {
        data = new TreeData();
        EventQueue.invokeLater(() -> {
            frame = new BSTFrame(sceneWidth, sceneHeight);
            frame.getInsertBtn().addActionListener(new insertHandler());
            frame.getSearchBtn().addActionListener(new searchHandler());
            frame.getDeleteBtn().addActionListener(new deleteHandler());
            frame.getClearBtn().addActionListener(new ClearHandler());
            frame.getSpeed().addChangeListener(new SpeedHandler());
            frame.render(data);
        });
    }

    private void insert(Node node, Node newNode) {
        setData(node, true, false, 1);
        if(node.value > newNode.value){
            setData(node, true, false, 2);
            if(node.left == null) {
                setData(node, true, false, 3);
                node.left = newNode;
                setData(node, false, false, 4);
            } else {
                setData(node,false,false, 6);
                insert(node.left, newNode);
            }
        } else if(node.value < newNode.value){
            setData(node, true, false, 7);
            if(node.right == null) {
                setData(node, true, false, 8);
                node.right = newNode;
                setData(node, false, false, 9);
            } else {
                setData(node,false, false, 11);
                insert(node.right, newNode);
            }
        } else {
            frame.getMsg().setText("Already exists!");
        }
        setData(node, false, false, -1);
    }


    private void insertToTree(Node newNode) {
        clear();
        //setData(null, false, false, 1);
        if(data.root == null) {
            //setData(null, false, false, 4);
            data.root = newNode;
            setData(data.root, false, false, -1);
            //frame.render(data);
        } else {
            //setData(data.root, false, false, 3);
            insert(data.root, newNode);
        }
        //setData(null, false, false, -1);
    }

    private Node searchedNode;
    private Node search(Node node, int val) {
        if(data.op.equals("search"))
            setData(node, true, false, 1);
        else
            setData(node, true, false, 2);
        if(node.value == val) {
            setData(node, false, true, 2);
            searchedNode = node;
            if(data.op.equals("search"))
                setData(node, false, true, -1);
            return node;
        } else if(node.value > val) {
            if(data.op.equals("search"))
                setData(node, false, false, 4);
            else
                setData(node, false, false, 2);
            if(node.left != null) {
                if(data.op.equals("search"))
                    setData(node, false, false, -1);
                return search(node.left, val);
            } else {
                if(data.op.equals("search"))
                    setData(node, false, false, -1);
                return null;
            }
        } else {
            if(data.op.equals("search"))
                setData(node, false, false, 7);
            else
                setData(node, false, false, 2);
            if(node.right != null) {
                if(data.op.equals("search"))
                    setData(node, false, false, -1);
                return search(node.right, val);
            } else {
                if(data.op.equals("search"))
                    setData(node, false, false, -1);
                return null;
            }
        }
     }

     private Node searchInTree(int val) {
        clear();
        if(data.root != null) {
            Node found = search(data.root, val);
            if(found == null)
                frame.getMsg().setText("Value not found.");
            return found;
        } else {
            return null;
        }

     }

     private Node searchParent(Node node, int val) {
        if((node.left != null && node.left.value == val) || (node.right != null && node.right.value == val)) {
            return node;
        } else {
            if(node.value > val && node.left != null) {
                return searchParent(node.left, val);
            } else if(node.value <= val && node.right != null) {
                return searchParent(node.right, val);
            } else {
                return null;
            }
        }
     }

     private Node searchParentInTree(int val) {
        if(data.root != null) {
            return searchParent(data.root, val);
        } else {
            return null;
        }
     }

     private void delete(int val) {
        if(data.root == null ) {
            return;
        } else {
            Node target  = searchInTree(val);
            if(target == null){
                rerender(-1);
                return;
            }
            if(data.root.left == null && data.root.right == null) {
                data.root = null;
                rerender(-1);
                return;
            }

            Node parent = searchParentInTree(val);
            rerender(3);
            if(target.left == null && target.right == null) {
                if(parent.left != null && parent.left.value == val) {
                    parent.left = null;
                    rerender(5);
                    rerender(-1);
                    return;
                } else if(parent.right != null && parent.right.value == val) {
                    parent.right = null;
                    rerender(5);
                    rerender(-1);
                    return;
                }
            } else if(target.left != null && target.right != null) {
                int minVal = deleteRightMin(target);
                target.value = minVal;
                rerender(7);
                rerender(-1);
                return;
            } else {
                if(target.left != null) {
                    if(parent == null) {
                        data.root = target.left;
                        rerender(9);
                        rerender(-1);
                        return;
                    }
                    if(parent.left != null && parent.left.value == val) {
                        parent.left = target.left;
                        rerender(9);
                        rerender(-1);
                        return;
                    } else if(parent.right != null && parent.right.value == val) {
                        parent.right = target.left;
                        rerender(9);
                        rerender(-1);
                        return;
                    }
                } else if(target.right != null) {
                    if(parent == null) {
                        data.root = target.right;
                        rerender(9);
                        rerender(-1);
                        return;
                    }
                    if(parent.left != null && parent.left.value == val) {
                        parent.left = target.right;
                        rerender(9);
                        rerender(-1);
                        return;
                    } else if(parent.right != null && parent.right.value == val) {
                        parent.right = target.right;
                        rerender(9);
                        rerender(-1);
                        return;
                    }
                }
            }
        }
     }

     private int deleteRightMin (Node node) {
        Node target = node.right;
        while(target.left != null) {
            target = target.left;
        }
        delete(target.value);
        return target.value;
     }

     private void clear() {
         if(searchedNode != null) {
             setData(searchedNode, false, false, -1);
         }
     }

    private void setData(Node node, boolean cur, boolean searched, int curLine) {
        if(node != null) {
            node.cur = cur;
            node.searched = searched;
        }
        data.curLine = curLine;
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
                    Node node = new Node(val);
                    thread = new Thread(() -> {
                        insertToTree(node);
                    });
                    thread.start();
                } catch (Exception exp) {
                    //frame.getInsertInput().setText("");
                    frame.getMsg().setText("Input integer only!");
                }
                frame.getInsertInput().setText("");
            }
        }
    }

    private class searchHandler implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(thread != null && thread.isAlive())
                thread.stop();

            data.op="search";
            frame.getMsg().setText("");
            String searchVal = frame.getSearchInput().getText();
            if(!searchVal.equals("") && searchVal != null) {
                try {
                    int val = Integer.valueOf(searchVal);
                    thread = new Thread(() -> {
                        searchInTree(val);
                    });
                    thread.start();
                } catch (Exception exp) {
                    //frame.getSearchInput().setText("");
                    frame.getMsg().setText("Input integer only!");
                }
                frame.getSearchInput().setText("");
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
                    });
                    thread.start();
                } catch (Exception exp) {
                    //frame.getDeleteInput().setText("");
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
