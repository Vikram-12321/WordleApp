import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;


public class wordbank {
    public static void main(String args[]) throws IOException {
        
        Scanner scnr = new Scanner(new File("wordbank.txt"));

        char letter = 'c';
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Letters\\letter_"+ letter + ".txt"), "utf-8"));
            

        while(scnr.hasNextLine()) {
            String word = scnr.nextLine();
            if (word.charAt(0) == letter){
                writer.write(word + "\n");
            }
        }
        writer.close();
        scnr.close();
    }
}
