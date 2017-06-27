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

    public static ArrayList<Boolean> bytes2bites(ArrayList<Byte> bytes) {
        ArrayList<Boolean> retVal = new ArrayList<>();

        for (int i = 0; i < bytes.size() - 1; i++) {
            byte curr = bytes.get(i);
            while (curr != 0) {
                int rem = curr % 2;
                curr = (byte) (curr / 2);
                if (rem == 1) {
                    retVal.add(true);
                } else {
                    retVal.add(false);
                }
            }
        }

        for (int i = 0; i < (bytes.get(bytes.size() - 1)); i++) {
            retVal.add(false);
        }



        return retVal;
    }


    public static byte[] bits2bytes(boolean[] arr) {

        byte leadingZeroes = 0;
        int ei = arr.length - 1;
        while (ei >= 0 && !arr[ei]) {
            ei--;
            leadingZeroes++;
        }


        byte[] retVal = new byte[((arr.length) / 7) + 2];
        for (int i = 0; i < retVal.length; i++) {
            boolean[] current = new boolean[7];
            for (int j = 0; i * 7 + j < arr.length && j < 7; j++) {
                current[j] = arr[i * 7 + j];
            }
            retVal[i] = bits2byte(current);

        }

        retVal[retVal.length - 1] = leadingZeroes;
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
