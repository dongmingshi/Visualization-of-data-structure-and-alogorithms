import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
        DefaultMutableTreeNode array = new DefaultMutableTreeNode("Array");
        root.add(array);
        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode("Selection Sort");
        array.add(leaf);
        leaf = new DefaultMutableTreeNode("Insertion Sort");
        array.add(leaf);
        leaf = new DefaultMutableTreeNode("Bubble Sort");
        array.add(leaf);
        leaf = new DefaultMutableTreeNode("Stack");
        array.add(leaf);
        leaf = new DefaultMutableTreeNode("Queue");
        array.add(leaf);

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
                            case "Stack":
                                new StackVisualizer(25);
                                break;
                            case "Queue":
                                new QueueVisualizer(25);
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
