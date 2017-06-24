import java.util.HashMap;

/**
 * Created by piyush0 on 24/06/17.
 */
public class BinaryTree {

    private class Node {
        Character data;
        Node left;
        Node right;

        public Node(Character data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    public BinaryTree(Character data, BinaryTree left, BinaryTree right) {
        this.root = new Node(data, null, null);
        if (left != null) {
            this.root.left = left.root;
        }
        if (right != null) {
            this.root.right = right.root;
        }
    }

    public void traversal(HashMap<Character, String> encoder, HashMap<String, Character> decoder) {

        traversal(encoder, decoder, "", this.root);

    }

    public String toString() {
        return this.toString(this.root);
    }

    private String toString(Node node) {
        if (node == null) {
            return "";
        }

        String retVal = "";

        if (node.left != null) {
            retVal += node.left.data + " => ";
        }
        retVal += node.data;
        if (node.right != null) {
            retVal += " <= " + node.right.data;
        }
        retVal += "\n";
        retVal += toString(node.left);
        retVal += toString(node.right);
        return retVal;
    }

    private void traversal(HashMap<Character, String> encoder, HashMap<String, Character> decoder, String osf, Node node) {

        if (node == null) {
            return;
        }

        traversal(encoder, decoder, osf + "0", node.left);

        if (node.left == null && node.right == null) {
            encoder.put(node.data, osf);
            decoder.put(osf, node.data);
        }

        traversal(encoder, decoder, osf + "1", node.right);

    }
}
