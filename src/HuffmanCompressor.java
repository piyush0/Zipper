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

    public ArrayList<Boolean> encode(String str) throws Exception {

        ArrayList<Boolean> retVal = new ArrayList<>();

        int i = 0;
        for (int u = 0; u < str.length(); u++) {
            char ch = str.charAt(u);
            String val = encoder.get(ch);

            if (val != null) {
                for (int j = 0; j < val.length(); j++) {
                    retVal.add(val.charAt(j) != '0');
                }
            } else {
                throw new Exception("No encoding present for " + ch);
            }

            i++;
        }

        return retVal;
    }

    public String decode(ArrayList<Boolean> arr) {
        StringBuilder sb = new StringBuilder();

        StringBuilder key = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i)) {
                key.append("1");
            } else {
                key.append("0");
            }

            if (decoder.containsKey(key.toString())) {
                sb.append(decoder.get(key.toString()));
                key = new StringBuilder();
            }
        }

        return sb.toString();
    }

    public int getMinimumVal() {

        Set<String> allStrings = decoder.keySet();
        int min = Integer.MAX_VALUE;
        for (String s : allStrings) {
            int twoPower = 1;
            int comparer = 0;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '1') {
                    comparer += twoPower;
                }
                twoPower *= 2;
            }

            if (comparer < min) {
                min = comparer;
            }
        }
        return min;

    }


}