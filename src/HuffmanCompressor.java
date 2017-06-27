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

    public int getMin() {
        int min = Integer.MAX_VALUE;
        Set<Character> allChars = this.freqMap.keySet();

        for (Character ch : allChars) {
            String enc = this.encoder.get(ch);

            int twoPower = 1;
            int chk = 0;
            for (int i = enc.length() - 1; i >= 0; i--) {
                if (enc.charAt(i) == '1') {
                    chk += twoPower;
                }
                twoPower *= 2;
            }
            System.out.println(chk);
            if (chk < min) {
                min = chk;
            }

        }

        return min;
    }

    public boolean[][] encode(String str) {

        boolean[][] retVal = new boolean[str.length()][];

        int i = 0;
        for (int u = 0; u < str.length(); u++) {
            char ch = str.charAt(u);
            String val = encoder.get(ch);

            retVal[i] = new boolean[val.length()];
            for (int j = 0; j < val.length(); j++) {

                retVal[i][j] = val.charAt(j) != '0';
            }

            i++;
        }

        return retVal;
    }

    public String decode(boolean[][] arr) {
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < arr.length; i++) {
            boolean[] current = arr[i];
            StringBuilder curr = new StringBuilder();

            for (int j = 0; j < current.length; j++) {
                if (current[j]) {
                    curr.append("1");
                } else {
                    curr.append("0");
                }
            }
            char decodedChar = decoder.get(curr.toString());
            sb.append(decodedChar);
        }

        return sb.toString();
    }
}