import java.io.*;
import java.util.BitSet;
import java.util.Scanner;

/**
 * Created by piyush0 on 24/06/17.
 */
public class Main {

    // /Users/piyush0/Desktop/hello.txt

    public static void main(String[] args) {
        Scanner scrn = new Scanner(System.in);

        FileDetails fileDetails = readFile(scrn);
        HuffmanCompressor huffmanCompressor = new HuffmanCompressor(fileDetails.fileContents);
        File encodedFile = createFile(getEncodedFileName(new File(fileDetails.filePath).getParent()));
        boolean[][] encoded = huffmanCompressor.encode();
        writeEncodedFile(encodedFile, encoded);
    }

    private static void writeEncodedFile(File file, boolean[][] content) {

        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.getAbsoluteFile()));

            for (int i = 0; i < content.length; i++) {
                boolean[] current = content[i];
                byte[] converted = Utils.bits2bytes(current);
                for (int j = 0; j < converted.length; j++) {
                    dos.writeByte(converted[j]);
                }
                dos.writeByte(-1);
            }

            dos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getEncodedFileName(String parentFile) {
        StringBuilder sb = new StringBuilder(parentFile);
        sb.append("/Encoded.txt");
        return sb.toString();
    }

    private static File createFile(String fileName) {
        File file = new File(fileName);
        System.out.println(fileName);

        try {
            if (file.createNewFile()) {
                return file;
            } else {
                System.err.println("Encoded file already present");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static FileDetails readFile(Scanner scrn) {
        FileDetails retVal = new FileDetails();

        System.out.println("Enter the path of the file you want to zip");
        String fileName = scrn.nextLine();
        retVal.filePath = fileName;
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(fileName));

            StringBuilder fileContents = new StringBuilder();
            String currentLine = br.readLine();

            while (currentLine != null) {
                fileContents.append(currentLine).append("\n");
                currentLine = br.readLine();
            }
            retVal.fileContents = fileContents.toString();
            return retVal;

        } catch (FileNotFoundException ex) {
            System.err.println("Invalid Path");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private static void display(boolean[][] encoded) {
        for (int i = 0; i < encoded.length; i++) {
            for (int j = 0; j < encoded[i].length; j++) {
                System.out.print(encoded[i][j] + " ");
            }
            System.out.println();
        }
    }

}
