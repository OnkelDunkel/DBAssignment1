import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;
import java.util.Scanner;

public class SimpleDB {
    private HashMap<String, String> indexMap = new HashMap<>();

    public void startDb() {

    }

    public void writeToDb(String key, String value) {

    }

    public String readFromDb(String key) {
        return "not implemented";
    }

    public static void main(String[] args) {

        SimpleDB db = new SimpleDB();
        System.out.println("Starting DB...");
        db.startDb();
        System.out.println("DB started.");
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("type one of following actions:");
            System.out.println("* 'write'");
            System.out.println("* 'read'");
            System.out.println("* 'exit'");
 
            String input = getUserInput("", sc);

            if (input.equals("write")) {
                String theKey = getUserInput("enter the key", sc);
                String theValue = getUserInput("enter the value", sc);
                db.writeToDb(theKey, theValue);
                System.out.println(theKey + theValue);
            } else if (input.equals("read")) {
                String theKey = getUserInput("enter the key", sc);
                System.out.println("read");

            } else if (input.equals("exit")) {
                System.out.println("exit");
                break;
            }
        }
    }

    private static String getUserInput(String msg, Scanner scanner){
        System.out.println(msg);
        scanner.hasNextLine();
        return scanner.nextLine();
    }
}
