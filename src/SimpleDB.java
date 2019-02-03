import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Scanner;

public class SimpleDB {
    private final String FILE = "simple.db";
    private HashMap<String, Long> indexMap;
    private int length = -1;

    public SimpleDB() throws FileNotFoundException {
        startDb();
    }

    private void startDb() throws FileNotFoundException {
        indexMap = new HashMap<>();
        try {
            RandomAccessFile file = new RandomAccessFile(FILE, "r");
            System.out.println("-Existing DB-file was indexed");
            long elementPos = 0;

            while (true) {
                try {
                    String line = file.readLine();
                    if (line == null) {
                        if (file.getFilePointer() == file.length()) break;
                        else continue;
                    }
                    int indexOf = line.indexOf(",");
                    String key = line.substring(0, indexOf);
                    indexMap.put(key, elementPos);
                    elementPos = file.getFilePointer() + 1;
                } catch (EOFException e) {
                    break;
                }  catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            RandomAccessFile newFile = new RandomAccessFile(FILE, "rw");
            System.out.println("-New DB-file created");
            try {
                newFile.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public boolean write(String key, String value) {

        try {
            RandomAccessFile newFile = new RandomAccessFile(FILE, "rw");
            try {
                long entryStart = newFile.length();
                newFile.seek(entryStart);
                newFile.write(key.getBytes("US-ASCII"));
                newFile.write(",".getBytes("US-ASCII"));
                newFile.write(value.getBytes("US-ASCII"));
                newFile.write("\n".getBytes("US-ASCII"));
                indexMap.put(key, entryStart);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String read(String key) {
        Long index = indexMap.get(key);
        if (index != null) {

            try {
                RandomAccessFile file = new RandomAccessFile(FILE, "r");
                file.seek(index);

                try {
                    String line = file.readLine();
                    return line.substring(line.indexOf(",") + 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println("-Starting DB...");
        SimpleDB db = null;
        try {
            db = new SimpleDB();
        } catch (FileNotFoundException e) {
            System.out.println("-Failed to start DB");
            return;
        }
        System.out.println("-DB started.\n");

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Type one of following actions:");
            System.out.println("'write', 'read' or 'exit'");
            String input = getUserInput("", sc);

            if (input.equals("write")) {
                String theKey = getUserInput("Enter the key", sc);
                String theValue = getUserInput("Enter the value", sc);
                boolean success = db.write(theKey, theValue);
                System.out.println("\n-Write success: " + success + "\n");
            } else if (input.equals("read")) {
                String theKey = getUserInput("Enter the key", sc);
                System.out.println("-" + db.read(theKey) + "\n");

            } else if (input.equals("exit")) {
                System.out.println("-Exiting...");
                break;
            } else {
                System.out.println("-Command not recognised!\n");
            }
        }
    }

    private static String getUserInput(String msg, Scanner scanner){
        System.out.println(msg);
        scanner.hasNextLine();
        String theLine = scanner.nextLine();
        if (theLine.contains("\n") || theLine.isBlank()) {
            System.out.println("-Invalid input. Try again");
            return getUserInput(msg, scanner);
        }
        return theLine;
    }
}
