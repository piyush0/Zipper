/**
 * Created by piyush0 on 27/06/17.
 */
public class DisplayUtils {
    public static void display(boolean[][] encoded) {
        for (int i = 0; i < encoded.length; i++) {
            for (int j = 0; j < encoded[i].length; j++) {
                System.out.print(encoded[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void display(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i]) {
                System.out.print(0 + ", ");
            } else {
                System.out.print(1 + ", ");
            }
        }
        System.out.println("END");
    }
}
