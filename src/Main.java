import java.io.*;
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

        FileDetails fileDetails = readFile(scrn);
        HuffmanCompressor huffmanCompressor = new HuffmanCompressor(fileDetails.fileContents);
        File encodedFile = createFile(getEncodedFileName(new File(fileDetails.filePath).getParent()));

        boolean[][] encoded = huffmanCompressor.encode(fileDetails.fileContents);
        writeEncodedFile(encodedFile, encoded);

        boolean[][] decoded = readEncodedFile(encodedFile);
        System.out.println(huffmanCompressor.decode(decoded));
    }

    private static boolean[][] readEncodedFile(File file) {

        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file.getAbsoluteFile()));

            ArrayList<ArrayList<Boolean>> retVal = new ArrayList<>();

            while (dis.available() > 0) {

                ArrayList<Byte> bytes = new ArrayList<>();

                byte current = dis.readByte();
                while (current != -1) {
                    bytes.add(current);
                    current = dis.readByte();
                }


                ArrayList<Boolean> arr = Utils.bytes2bites(bytes);
                retVal.add(arr);
            }
            return arrayToList(retVal);

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

}
