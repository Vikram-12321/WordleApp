import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Writer;


public class wordbank {
    public static void createFile(String args[]) throws IOException {
        
        Scanner scnr = new Scanner(new File("wordbank.txt"));

        char letter = 'z';
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("letter_"+ letter + ".txt"), "utf-8"));
            

        while(scnr.hasNextLine()) {
            String word = scnr.nextLine();
            if (word.charAt(0) == letter){
                writer.write(word + "\n");
            }
        }
        writer.close();
        scnr.close();
    }

    public static boolean checkWord(String word) throws FileNotFoundException{

        char letter = Character.toLowerCase(word.charAt(0));
        Scanner txtscan = new Scanner(new File("Letters\\letter_"+ letter + ".txt"));

        while(txtscan.hasNextLine()){
            String str = txtscan.nextLine();
            if(str.indexOf(word) != -1){
                return true;
            } 
        }
        return false;
    }

    public static void main(String args[]) throws IOException {
        
        System.out.println(checkWord("speak"));
    }
}
