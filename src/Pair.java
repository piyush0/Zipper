/**
 * Created by piyush0 on 24/06/17.
 */
public class Pair implements Comparable<Pair> {

    @Override
    public String toString() {
        return this.value + "";
    }

    BinaryTree binaryTree;
    Integer value;

    public Pair() {
    }

    public Pair(BinaryTree binaryTree, Integer value) {
        this.binaryTree = binaryTree;
        this.value = value;
    }

    @Override
    public int compareTo(Pair o) {
        return this.value - o.value;
    }
}
