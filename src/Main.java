import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

/**
 * Created by piyush0 on 24/06/17.
 */
public class Main {

    // /Users/piyush0/Desktop/hello.txt

    public static void main(String[] args) {
        Scanner scrn = new Scanner(System.in);


        HuffmanCompressor huffmanCompressor = new HuffmanCompressor(readRelativeFile("Source.txt"));

        System.out.println(huffmanCompressor.getEncoder());
        System.out.println("***********");

        FileDetails fileDetails = readFile(scrn);
        File encodedFile = createFile(getEncodedFileName(new File(fileDetails.filePath).getParent()));

        ArrayList<Boolean> encoded = null;
        try {
            encoded = huffmanCompressor.encode(fileDetails.fileContents);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writeEncodedFile(encodedFile, encoded);

        ArrayList<Boolean> decoded = readEncodedFile(encodedFile);
        System.out.println(huffmanCompressor.decode(decoded));
    }

    private static ArrayList<Boolean> readEncodedFile(File file) {

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file.getAbsoluteFile()));

            ArrayList<Byte> bytes = new ArrayList<>();
            while (dis.available() > 0) {
                bytes.add(dis.readByte());
            }

            ArrayList<Boolean> retVal = Utils.byte2boolBTR(bytes);
            return retVal;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean[][] arrayToList(ArrayList<ArrayList<Boolean>> arr) {
        boolean[][] retVal = new boolean[arr.size()][];
        for (int i = 0; i < arr.size(); i++) {
            retVal[i] = new boolean[arr.get(i).size()];
            for (int j = 0; j < arr.get(i).size(); j++) {
                retVal[i][j] = arr.get(i).get(j);
            }

        }

        return retVal;
    }

    private static void writeEncodedFile(File file, ArrayList<Boolean> content) {

        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.getAbsoluteFile()));

            ArrayList<Byte> converted = Utils.bool2byteBTR(content);

            for (int i = 0; i < converted.size(); i++) {
                dos.writeByte(converted.get(i));
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

    private static String readRelativeFile(String filename) {
        try {
            URL url = Main.class.getResource(filename);
            File file = new File(url.getPath());
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
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
