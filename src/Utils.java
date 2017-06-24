import java.util.ArrayList;

/**
 * Created by piyush0 on 25/06/17.
 */
public class Utils {
    public static void display(byte[] arr) {
        for (byte b : arr) {
            System.out.print(b + " ");
        }
    }

    public static boolean[] bytes2bites(ArrayList<Byte> bytes){
        System.out.println(bytes);
        return null;
    }


    public static byte[] bits2bytes(boolean[] arr) {

        byte[] retVal = new byte[((arr.length) / 7) + 1];
        for (int i = 0; i < retVal.length; i++) {
            boolean[] current = new boolean[7];
            for (int j = 0; i * 7 + j < arr.length && j < 7; j++) {
                current[j] = arr[i * 7 + j];
            }
            retVal[i] = bits2byte(current);

        }
        return retVal;
    }


    private static byte bits2byte(boolean[] arr) {

        byte retVal = 0;
        int twoPower = 1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i]) {
                retVal += twoPower;
            }
            twoPower *= 2;
        }


        return retVal;

    }
}
