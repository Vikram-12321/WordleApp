import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class wordbank {
    public static void main(String args[]) throws IOException {
        FileOutputStream fileStream = null;
        PrintWriter outFS = null;

        fileStream = new FileOutputStream("words.txt");
        outFS = new PrintWriter(fileStream);

        outFS.println("Hello");
        outFS.println("1 2 3");

        outFS.close();
    }
}
