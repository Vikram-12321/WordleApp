import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AppFunctions {
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
}
