package Demo;

import Demo.BubbleSort.BubbleSortVisualizer;
import Demo.Graph.*;
import Demo.InsertionSort.InsertionSortVisualizer;
import Demo.MergeSort.MergeSortVisualizer;
import Demo.Queue.QueueVisualizer;
import Demo.SelectionSort.SelectionSortVisualizer;
import Demo.Stack.StackVisualizer;
import Demo.Tree.AVLVisualizer;
import Demo.Tree.BSTVisualizer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Nav extends JFrame {
    public Nav() {
        super("Data Structure and Algorithm Visualization");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Data Structure");
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Linear");
        root.add(node);
        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode("Selection Sort");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Insertion Sort");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Bubble Sort");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Merge Sort");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Stack");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Queue");
        node.add(leaf);
        node = new DefaultMutableTreeNode("Tree");
        root.add(node);
        leaf = new DefaultMutableTreeNode("Binary Search Tree");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("AVL Tree");
        node.add(leaf);
        node = new DefaultMutableTreeNode("Graph");
        root.add(node);
        leaf = new DefaultMutableTreeNode("Depth First Search");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Breadth First Search");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Prim");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Kruskal");
        node.add(leaf);
        leaf = new DefaultMutableTreeNode("Dijkstra");
        node.add(leaf);

        JTree menu = new JTree(root);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(menu);
        add(scrollPane);

        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JTree tree = (JTree) e.getSource();
                    int rowLocation = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath treePath = tree.getPathForRow(rowLocation);
                    if(treePath != null) {
                        TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
                        String nodeName = treeNode.toString();
                        switch (nodeName){
                            case "Selection Sort":
                                new SelectionSortVisualizer(1024,568, 30);
                                break;
                            case "Insertion Sort":
                                new InsertionSortVisualizer(1024,568, 30);
                                break;
                            case "Bubble Sort":
                                new BubbleSortVisualizer(1024,568, 30);
                                break;
                            case "Merge Sort":
                                new MergeSortVisualizer(1024, 568, 30);
                                break;
                            case "Stack":
                                new StackVisualizer(25);
                                break;
                            case "Queue":
                                new QueueVisualizer(25);
                                break;
                            case "Binary Search Tree":
                                new BSTVisualizer(1024, 568);
                                break;
                            case "AVL Tree":
                                new AVLVisualizer(1024,568);
                                break;
                            case "Depth First Search":
                                new DFSVisualizer(1024, 568);
                                break;
                            case "Breadth First Search":
                                new BFSVisualizer(1024, 568);
                                break;
                            case "Prim":
                                new PrimVisualizer(1024, 568);
                                break;
                            case "Kruskal":
                                new KruskalVisualizer(1024, 568);
                                break;
                            case "Dijkstra":
                                new DijkstraVisualizer(1024, 568);
                                break;
                        }
                    }
                }
            }
        });

        pack();
        setVisible(true);
        setSize(new Dimension(500, 500));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Nav();
    }

}
