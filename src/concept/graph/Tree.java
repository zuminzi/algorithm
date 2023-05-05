package concept.graph;


import java.util.ArrayList;
import java.util.List;

/**
 * 단방향 연결 리스트
 * @see <a href="https://readerr.tistory.com/35"></a>
 */
public class Tree {
    int count;

    public Tree() {
        count = 0;
    }

    public class Node {
        Object data;
        Node left;
        Node right;

        // 생성 시 매개변수를 받아 초기화하는 방법으로만 선언 가능
        public Node(Object data) {
            this.data = data;
            left = null;
            right = null;
        }

        public void addLeft(Node node){
            left = node;
            count++;
        }

        public void addRight(Node node){
            right = node;
            count++;
        }

        public void deleteLeft() {
            left = null;
            count--;
        }

        public void deleteRight() {
            right = null;
            count--;
        }

    }

    public Node addNode(Object data) {
        Node n  = new Node(data);
        return n;
    }

    /**
     * @return none
     */
    public void preOrder(Node node) {
        if(node == null) {
            return;
        }

        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * @return List
     */
    public List<Object> preOrder(Node node, List<Object> list) {
        if(node != null) {
            list.add(node.data);
            preOrder(node.left, list);
            preOrder(node.right, list);
        }
        return list;
    }

    public List<Object> inOrder(Node node, List<Object> list){
        if(node != null) {
            inOrder(node.left, list);
            list.add(node.data);
            inOrder(node.right, list);
        }
        return list;
    }

    public List<Object> postOrder(Node node, List<Object> list){
        if(node != null) {
            postOrder(node.left, list);
            postOrder(node.right, list);
            list.add(node.data);
        }
        return list;
    }

    public static void main (String[] args) {
        // 트리 생성
        Tree tree = new Tree();

        // 노드 생성
        Node node1 = tree.addNode(1);
        Node node2 = tree.addNode(2);
        Node node3 = tree.addNode(3);
        Node node4 = tree.addNode(4);
        Node node5 = tree.addNode(5);
        Node node6 = tree.addNode(6);
        Node node7 = tree.addNode(7);

        // 트리 연결관계 생성
        /*  트리 모양
         *        1
         *     2     3
         *   4  5  6   7
         */

        node1.addLeft(node2);
        node1.addRight(node3);
        node2.addLeft(node4);
        node2.addRight(node5);
        node3.addLeft(node6);
        node3.addRight(node7);

        // 순회(print)
        tree.preOrder(node1);

        // 순회(return)
        List<Object> preOrder = tree.preOrder(node1, new ArrayList<>());
        System.out.println(preOrder);
    }
}
