package Demo.Tree;

import Demo.Data;

public class TreeData extends Data {
    public Node root;
    public String op = "";
    //int count = 0;


//    public void add(Node node) {
//        if(root == null) {
//            root = node;
//        } else {
//            root.add(node);
//        }
//        //count++;
//    }

    public int getTreeHeight() {
        return root.height();
    }

}

