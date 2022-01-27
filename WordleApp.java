import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class WordleApp extends JFrame implements ActionListener {
    int currentIndex;
    JTextField[] inputField = new JTextField[30];
    JLabel myLabel;
    int guessNumber = 0;
    private JButton enterButton;
    private static final int GAP = 1;
    private String wordleWord;
    private int letterCount [] = new int[26];
    private int lettersUsed [] = new int[26];
    char alphabet [] = new char [26];

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

    private WordleApp() throws IOException {
        GridBagConstraints positionConstants;
        int i, j;
        setLayout(new GridBagLayout());
        positionConstants = new GridBagConstraints();
        wordleWord = "APPLE";
        // Initialize the values of the arrays; alphabet to uppercase letters; letterCount and letterUsed to zero
        for (i=0; i< 26; i++){
            alphabet[i] = (char)(i+65);
            letterCount[i] = 0;
            lettersUsed[i] = 0;
        }

        for (i = 0; i < 26; i++) {
            for (j = 0; j < wordleWord.length(); j++) {
                if (wordleWord.charAt(j) == alphabet[i])
                    letterCount[i] += 1;
            }
        }

        for(i =0;i< 30; ++i) {
            inputField[i] = new JTextField(1);
            inputField[i].setEditable(true);
            inputField[i].setText("");
            inputField[i].setFont(new Font("Arial", Font.BOLD, 70));
            inputField[i].setBackground(Color.DARK_GRAY);
            inputField[i].setForeground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
            inputField[i].setBorder(border);
            positionConstants.gridx = i % 5;
            positionConstants.gridy = i / 5;
            add(inputField[i], positionConstants);
            inputField[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent event) {
                    Object source = event.getSource();
                    JTextField field = (JTextField) source;
                    char inputChar = event.getKeyChar();
                    if (Character.isLowerCase((inputChar))) {
                        event.setKeyChar((Character.toUpperCase(inputChar)));
                    }
                    if (inputChar == 0) {
                        currentIndex -= 1;
                    }
                    // if delete or backspace move to previous column

                    if (inputChar == 8) {
                        if (Math.floorMod(currentIndex,5) != 0) {
                            currentIndex -= 1;
                            inputField[currentIndex].setText(field.getText().substring(0,0));
                        }
                        inputField[currentIndex].requestFocus();
                        // otherwise set focus in next column
                    } else {
                        if (Math.floorMod(currentIndex,5) != 4) {
                            currentIndex += 1;
                            inputField[currentIndex].requestFocus();

                        } else{
                            inputField[currentIndex].setText(field.getText().substring(0,0));
                            inputField[currentIndex].requestFocus();
                        }
                    }
                }
        });
    }


    // myLabel = new JLabel("word not in databank");
    // myLabel.setFont(new Font("Arial",Font.BOLD, 30));
    // positionConstants.gridx = 0;
    // positionConstants.gridy = 0;
    // add(myLabel,positionConstants);
    // myLabel.setVisible(true);

    // enterButton = new JButton("Enter");
    // enterButton.setFont(new Font("Aldous Vertical", Font.PLAIN, 10));
    // enterButton.setPreferredSize(new Dimension(80, 40));
    // positionConstants.gridx = 7;
    // positionConstants.gridy = 2;
    // positionConstants.insets = new Insets(4, 4, 4, 4);
    // add(enterButton, positionConstants);
    // enterButton.addActionListener(this);

    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            System.out.println("Hello");
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // declaring variables for my loop
        int i, count;
        StringBuilder word = new StringBuilder();

        char [] inputChar = new char[5];
        for(i = 0; i < 5; i++) {
            inputChar[i] = inputField[5*guessNumber + i].getText().charAt(0);
            System.out.println("inputChar[i]" +inputChar[i]);
            word.append(inputChar[i]);
        }

        String singleString = word.toString();
        try {
            if (checkWord(singleString)){

<<<<<<< HEAD
// For loop for changing colors
        }
        for(i = 0; i < 5; i++) {
            if(inputChar[i] == wordleWord.charAt(i)){
                inputField[5*guessNumber + i].setBackground(Color.GREEN);
            }
           // else if (inputChar[i] != wordleWord.charAt(i))   {


             //   inputField[5*guessNumber + i].setBackground(Color.YELLOW);
            //}
            else if (inputChar[i] != wordleWord.charAt(i))   {
                inputField[5*guessNumber + i].setBackground(Color.GRAY);
=======
            // For loop for changing colors
                for(i = 0; i < 5; i++) {
                    if(inputChar[i] == wordleWord.charAt(i)){
                        inputField[5*guessNumber + i].setBackground(Color.GREEN);
                    }
                    else if (inputChar[i] == wordleWord.charAt(i) )   {
                        inputField[5*guessNumber + i].setBackground(Color.YELLOW);
                    }
                    else if (inputChar[i] != wordleWord.charAt(i))   {
                        inputField[5*guessNumber + i].setBackground(Color.GRAY);
                    }
                    
                
                }
                for (i = 0; i < inputChar.length; ++i) {

                }
                guessNumber += 1;
                currentIndex = guessNumber * 5;
                inputField[currentIndex].requestFocus();
            } else{
                System.out.print("not a word");
>>>>>>> 9877fd5296556a1401e4667e59fcfa26fa1a284a
            }
        } catch (FileNotFoundException e1) {}

    }
    public static void main(String[] args) throws IOException{
        WordleApp myFrame = new WordleApp();

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //myFrame.setPreferredSize(new Dimension(800, 1000));
        myFrame.setBackground(Color.WHITE);
        // Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        // myFrame.setBorder(border);
        myFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        myFrame.pack();
        myFrame.setVisible(true);
    }
}