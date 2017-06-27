import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by piyush0 on 24/06/17.
 */
public class Main {

    // /Users/piyush0/Desktop/hello.txt
    // /Users/piyush0/Desktop/encoded.txt

    public static void main(String[] args) {
        Scanner scrn = new Scanner(System.in);

        HuffmanCompressor huffmanCompressor = new HuffmanCompressor(readRelativeFile("Source.txt"));
        System.out.println(huffmanCompressor.getEncoder());
        System.out.println("0 for encoding/1 for decoding");
        int choice = scrn.nextInt();
        scrn.nextLine();
        if (choice == 0) {
            System.out.println("Enter the path of the file you want to compress ");
            String filePath = scrn.nextLine();
            File file = new File(filePath);
            String fileContents = readFile(file);
            File encodedFile = createFileAt(file.getParent(), "encoded");

            ArrayList<Boolean> encoded = null;
            try {
                encoded = huffmanCompressor.encode(fileContents);
            } catch (Exception e) {
                e.printStackTrace();
            }
            writeEncodedFile(encodedFile, encoded);
        } else {
            System.out.println("Enter the path of compressed file");
            String filePath = scrn.nextLine();
            File file = new File(filePath);
            ArrayList<Boolean> decoded = readEncodedFile(file);
            String content = huffmanCompressor.decode(decoded);
            System.out.println(content);

            File decodedFile = createFileAt(file.getParent(), "decoded");
            writeDecodedFile(decodedFile, content);

        }
    }

    private static ArrayList<Boolean> readEncodedFile(File file) {

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file.getAbsoluteFile()));

            ArrayList<Byte> bytes = new ArrayList<>();
            while (dis.available() > 0) {
                bytes.add(dis.readByte());
            }

            ArrayList<Boolean> retVal = Utils.bytes2bit(bytes);
            return retVal;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void writeDecodedFile(File file, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeEncodedFile(File file, ArrayList<Boolean> content) {

        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.getAbsoluteFile()));

            ArrayList<Byte> converted = Utils.bits2bytes(content);

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

    private static File createFileAt(String parent, String relativeName) {

        String path = parent + "/" + relativeName + ".txt";
        File file = new File(path);

        try {
            int i = 1;
            while (!file.createNewFile()) {
                file = new File(parent + "/" + relativeName + i + ".txt");
                i++;
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String readFile(File file) {

        try {
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
