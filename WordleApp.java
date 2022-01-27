import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class WordleApp extends JFrame implements ActionListener {
    int currentIndex;
    JTextField[] inputField = new JTextField[30];
    JLabel myLabel;
    int guessNumber = 0;
    private JButton enterButton;
    private String wordleWord;
    private int letterCount [] = new int[26];
    private int lettersUsed [] = new int[26];
    char alphabet [] = new char [26];

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

    for(i =0;i< 30; ++i)
    {
        inputField[i] = new JTextField(1);
        inputField[i].setEditable(true);
        inputField[i].setText("");
        inputField[i].setFont(new Font("Arial", Font.BOLD, 34));
        inputField[i].setBackground(Color.WHITE);
        inputField[i].setForeground(Color.BLACK);
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
    // myLabel.setFont(new Font("Arial",Font.PLAIN, 20));
    // positionConstants.gridx = 6;
    // positionConstants.gridy = 2;
    // add(myLabel,positionConstants);

    enterButton = new JButton("Enter");
    enterButton.setFont(new Font("Aldous Vertical", Font.PLAIN, 10));
    enterButton.setPreferredSize(new Dimension(80, 40));
    positionConstants.gridx = 6;
    positionConstants.gridy = 6;
    positionConstants.insets = new Insets(4, 4, 4, 4);
    add(enterButton, positionConstants);
    enterButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int i;
        char [] inputChar = new char[5];
        for(i = 0; i < 5; i++) {
            inputChar[i] = inputField[5*guessNumber + i].getText().charAt(0);
            System.out.println("inputChar[i]" +inputChar[i]);

// declaring variables for my loop
        int count;

// For loop for changing colors
        }
        for(i = 0; i < 5; i++) {
            if(inputChar[i] == wordleWord.charAt(i)){
                inputField[5*guessNumber + i].setBackground(Color.GREEN);
            }
            else if (inputChar[i] == wordleWord.charAt(i) && )   {
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
    }
    public static void main(String[] args) throws IOException{
        WordleApp myFrame = new WordleApp();

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setPreferredSize(new Dimension(400, 600));
        myFrame.pack();
        myFrame.setVisible(true);
    }
}