import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by piyush0 on 24/06/17.
 */
public class HuffmanCompressor {

    private HashMap<Character, Integer> freqMap;
    private PriorityQueue<Pair> minHeap;
    private HashMap<Character, String> encoder;
    private HashMap<String, Character> decoder;

    HuffmanCompressor(String source) {
        setFreqMap(source);
        createMinHeap();
        reduceMinHeap();
        createEnoderDecoder();
    }

    public HashMap<Character, Integer> getFreqMap() {
        return freqMap;
    }

    public PriorityQueue<Pair> getMinHeap() {
        return minHeap;
    }

    public HashMap<Character, String> getEncoder() {
        return encoder;
    }

    public HashMap<String, Character> getDecoder() {
        return decoder;
    }

    private void setFreqMap(String source) {
        freqMap = new HashMap<>();

        for (int i = 0; i < source.length(); i++) {
            char cc = source.charAt(i);
            if (freqMap.containsKey(cc)) {
                int oldFreq = freqMap.get(cc);
                freqMap.put(cc, oldFreq + 1);
            } else {
                freqMap.put(cc, 1);
            }
        }
    }

    private void createMinHeap() {

        minHeap = new PriorityQueue<>();
        Set<Character> allCharacters = freqMap.keySet();
        for (Character ch : allCharacters) {
            BinaryTree bt = new BinaryTree(ch, null, null);
            Pair pair = new Pair(bt, freqMap.get(ch));
            minHeap.add(pair);
        }
    }

    private void reduceMinHeap() {
        while (minHeap.size() != 1) {
            Pair p1 = minHeap.poll();
            Pair p2 = minHeap.poll();

            Pair ptba = new Pair(new BinaryTree('\0', p1.binaryTree, p2.binaryTree), p1.value + p2.value);
            minHeap.add(ptba);
        }
    }

    private void createEnoderDecoder() {
        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();
        BinaryTree finalTree = this.minHeap.poll().binaryTree;
        finalTree.traversal(this.encoder, this.decoder);
    }

    public boolean[][] encode() {
        Set<Character> allChars = this.encoder.keySet();
        boolean[][] retVal = new boolean[allChars.size()][];

        int i = 0;
        for (Character ch : allChars) {
            String val = encoder.get(ch);

            retVal[i] = new boolean[val.length()];
            for (int j = 0; j < val.length(); j++) {
                retVal[i][j] = val.charAt(j) != '0';
            }
            i++;
        }

        return retVal;
    }
}
