package Demo.Tree;

public class Node {
    public int value;
    public Node left;
    public Node right;
    public int x;
    public int y;
    public boolean cur;
    public boolean searched;
    public boolean isBalance = true;


    public Node(int value) {
        this.value = value;
        this.cur = false;
        this.searched = false;

    }


//    public void add(Node node) {
//        if(node.value < this.value) {
//            if(this.left == null) {
//                this.left = node;
//            } else {
//                this.left.add(node);
//            }
//        } else {
//            if(this.right == null) {
//                this.right = node;
//            } else {
//                this.right.add(node);
//            }
//        }
//    }

    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    public int leftHeight() {
        if(left == null)
            return 0;
        return left.height();
    }

    public int rightHeight() {
        if(right == null)
            return 0;
        return right.height();
    }

    public void leftRotate() {
        //创建新的节点当前根节点
        Node newNode = new Node(value);
        //新的节点的左子树设置成当前节点的左子树
        newNode.left = left;
        //新节点的右子树设置成当前节点右子树的左子树
        newNode.right = right.left;
        //把当前节点的值替换成右子节点值
        value = right.value;
        //当前节点的右子树设置成右子树的右子树
        right = right.right;
        //当前节点的左子树设置为新节点
        left = newNode;
    }

    public void rightRotate() {
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }
}
