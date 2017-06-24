import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by piyush0 on 24/06/17.
 */
public class Main {

    // /Users/piyush0/Desktop/Faster IntelliJ.txt

    public static void main(String[] args) {
//        Scanner scrn = new Scanner(System.in);
//        String fileContents = readFile(scrn);
        String str = "jndsfkjkbnsjksbfjkbsdfbjksdkbjdfsakjbdsafkjbdfsabjk";
        HuffmanCompressor huffmanCompressor = new HuffmanCompressor(str);
        System.out.println(huffmanCompressor.getFreqMap());
        System.out.println(huffmanCompressor.getMinHeap());
        System.out.println(huffmanCompressor.getEncoder());
        System.out.println(huffmanCompressor.getDecoder());

    }

    private static String readFile(Scanner scrn) {
        System.out.println("Enter the path of the file you want to zip");
        String fileName = scrn.nextLine();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(fileName));

            StringBuilder fileContents = new StringBuilder();
            String currentLine = br.readLine();

            while (currentLine != null) {
                fileContents.append(currentLine).append("\n");
                currentLine = br.readLine();
            }

            return fileContents.toString();

        } catch (FileNotFoundException ex) {
            System.err.println("Invalid Path");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
