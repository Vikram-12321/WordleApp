
/**
* The WordleApp program creates a clone of the very popular game Wordle,
* which you can play at https://www.powerlanguage.co.uk/wordle/.
*
* @authors Vikram Bhojanala, Kayetan Protas, Joshua Macdonald
* @version 1.0
* @since   2022-01-27
*/

import javax.swing.*; // Import  Swing Class
import java.awt.*; // Import AWT Class
import javax.swing.border.Border; // Import Border Class
import java.awt.event.ActionEvent; // Import ActionEvent Class
import java.awt.event.ActionListener; // Import ActionListener Class
import java.awt.event.KeyAdapter; // Import KeyAdapter Class
import java.awt.event.KeyEvent; // Import KeyEvent Class
import java.io.FileNotFoundException; // Import FileNotFoundException Class
import java.io.IOException; // Import IOException Class
import java.io.File; // Import File Class 
import java.util.Scanner; // Import the Scanner Class


public class WordleApp extends JFrame implements ActionListener {
    static JTextField[] inputField = new JTextField[30];
    private String wordleWord;
    private int letterCount[] = new int[26];
    private int lettersUsed[] = new int[26];
    char alphabet[] = new char[26];
    int currentIndex;
    JLabel myLabel;
    int guessNumber = 0;
    static int seconds = 0;
    // Variable definitions

    /**
    * The setErrorMsg method creates an Error Message using a pop up window, with the text param.
    * @param  text  the error message in String format.
    */
    public static void setErrorMsg(String text) {
        Toolkit.getDefaultToolkit().beep(); 
        JOptionPane optionPane = new JOptionPane(text, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        optionPane.setPreferredSize(new Dimension(500, 600));
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true);
    }

    /**
    * The setWinMsg method creates an Informational Message using a pop up window, 
    * telling the user that they have won the game.
    * The message includes the # of seconds and guesses they took to finish the game.
    * @param seconds  the # of seconds the user took.
    * @param seconds  the # of currentIndex of the user.
    */
    public static void setWinMsg(int seconds, int currentIndex) {
        int guesses = (currentIndex+1)/5; // Calculation of guesses using the currentIndex param.
        Toolkit.getDefaultToolkit().beep(); 
        JOptionPane optionPane = new JOptionPane("You took " + seconds + " seconds!\n" + "You took " + guesses + " guesses!", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("You have Won!");
        optionPane.setPreferredSize(new Dimension(300, 300)); 
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true); 
    }

    /**
    * The checkWord checks wether the word the user has inputted is valid or not
    * by checking the local 5 letter word database.
    * @param word the word the user has inputted in String format.
    * @return true if the word exists and false if it does not.
    */
    public static boolean checkWord(String word) throws FileNotFoundException {
        word = word.toLowerCase(); // sets the word to lowercase
        char letter = Character.toLowerCase(word.charAt(0)); // Takes the first letter of the word
        Scanner txtscan = new Scanner(new File("Letters\\letter_" + letter + ".txt")); // Locates the file that contains the words that start with the same letter

        while (txtscan.hasNext()) {
            String str = txtscan.next();
            if (str.equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
    * The generateWordle method generates a random Wordle.
    * @return the word that is generated.
    */
    public static String generateWordle() throws FileNotFoundException {
        int num = (int)(Math.random() * (5750 - 1 + 1) + 1); // generates a number between 1 and 5750, which is the number of words in the wordbank
        String word = "";
        int count = 0;

        Scanner txtscan = new Scanner(new File("wordbank.txt"));
        while (count != num) {
            count++;
            word = txtscan.next();
        }
        word = word.toUpperCase();
        return word;
    }

    /**
    * The resetFocusParams method only allows the user to edit the 5 letter line they are currently focused on.
    * It disables the user to edit any of the input fields above or below the line.
    * @param currentIndex is the current index of the user.
    */
    private void resetFocusParams(int currentIndex) {
        int IntValue = (int) Math.round((Math.floor(currentIndex / 5.0)) * 5.0);
        int IntValueMax = IntValue + 5;
        System.out.println(IntValue);

        for (int i = 0; i < 30; i++) {
            if (i >= IntValue && i < IntValueMax) {
                inputField[i].setEditable(true);

            } else {
                inputField[i].setEditable(false);
            }
        }
    }

    private WordleApp() throws IOException {
        GridBagConstraints positionConstants;
        int i, j;
        setLayout(new GridBagLayout());
        positionConstants = new GridBagConstraints();
        wordleWord = generateWordle();

        for (i = 0; i < 26; i++) {
            alphabet[i] = (char)(i + 65);
            letterCount[i] = 0;
            lettersUsed[i] = 0;
        }

        for (i = 0; i < 26; i++) {
            for (j = 0; j < wordleWord.length(); j++) {
                if (wordleWord.charAt(j) == alphabet[i])
                    letterCount[i] += 1;
            }
        }

        for (i = 0; i < 30; ++i) {
            inputField[i] = new JTextField(1);
            inputField[i].setEditable(false);

            int IntValue = (int) Math.round((Math.floor(currentIndex / 5.0)) * 5.0);
            int IntValueMax = IntValue + 5;

            if (i >= IntValue && i < IntValueMax) {
                inputField[i].setEditable(true);
            }

            inputField[i].setText("");
            inputField[i].setFont(new Font("Arial", Font.BOLD, 80));
            inputField[i].setBackground(Color.WHITE);
            inputField[i].setForeground(Color.BLACK);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
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
                    if (field.getText().length() >= 1) {
                        event.consume();
                    }
                    if (inputChar == 0) {
                        currentIndex -= 1;
                    }

                    if (inputChar == 8) {
                        if (Math.floorMod(currentIndex, 5) != 0) {
                            currentIndex -= 1;
                            inputField[currentIndex].setText(field.getText().substring(0, 0));
                        }
                        inputField[currentIndex].requestFocus();
                    } else {
                        if (Math.floorMod(currentIndex, 5) != 4) {
                            currentIndex += 1;
                            inputField[currentIndex].requestFocus();

                        } else {
                            inputField[currentIndex].setText(field.getText().substring(0, 0));
                            inputField[currentIndex].requestFocus();
                        }
                    }
                }
            });
        }

        JButton EnterButton;
        EnterButton = new JButton("Enter");
        EnterButton.setFont(new Font("Aldous Vertical", Font.PLAIN, 10));
        EnterButton.setPreferredSize(new Dimension(70, 40));
        positionConstants.gridx = 5;
        positionConstants.gridy = 5;
        positionConstants.insets = new Insets(4, 4, 4, 4);
        add(EnterButton, positionConstants);
        EnterButton.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent event) {

        StringBuilder word = new StringBuilder();
        char[] inputChar = new char[5];
        System.out.println(wordleWord);

        for (int i = 0; i < 5; i++) {
            inputChar[i] = inputField[5 * guessNumber + i].getText().charAt(0);
            word.append(inputChar[i]);
        }

        String singleString = word.toString();
        char[][] userArray = new char[5][2];
        char[][] appArray = new char[5][2];

        try {
            if (checkWord(singleString)) {

                for (int i = 0; i < 5; i++) {
                    userArray[i][0] = wordleWord.charAt(i);
                    appArray[i][0] = inputChar[i];
                    if (inputChar[i] == wordleWord.charAt(i)) {
                        inputField[5 * guessNumber + i].setBackground(new Color(106, 170, 100));
                        userArray[i][1] = 'u';
                        appArray[i][1] = 'u';
                    } else {
                        userArray[i][1] = 'a';
                        appArray[i][1] = 'a';
                    }
                }
                if (singleString.equals(wordleWord)) {
                    setWinMsg(seconds, currentIndex);
                }

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (userArray[i][1] == 'a') {
                            if (appArray[j][1] == 'a') {
                                if (appArray[j][0] == userArray[i][0]) {
                                    inputField[5 * guessNumber + j].setBackground(new Color(201, 180, 88));
                                    userArray[i][1] = 'u';
                                    appArray[j][1] = 'u';
                                } else {
                                    inputField[5 * guessNumber + j].setBackground(new Color(129, 131, 132));
                                }
                            }
                        }
                    }
                }

                guessNumber += 1;
                currentIndex = guessNumber * 5;
                inputField[currentIndex].requestFocus();
                resetFocusParams(currentIndex);

            } else {
                setErrorMsg("Word does not exist.");
            }
        } catch (FileNotFoundException e1) {}

    }

    /**
    * The timer method creates a Thread timer that is used to track the entire time the program has been running,
    * by adding to seconds every 1000 milliseconds.
    * Once the program has stopped, the method stops adding to seconds.
    */
    public static Thread timer = new Thread() {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                seconds += 1;
            }
        }
    };

    /**
    * The main method creates a JFrame called myFrame that contains all the contents of the app.
    */
    public static void main(String[] args) throws IOException {
        timer.start(); // starts the timer
        WordleApp myFrame = new WordleApp(); // creates the JFrame
        myFrame.setResizable(false); // Doesn't allow the window to be resized
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //  Exit the application using the System exit method once the window has been closed
        myFrame.setBackground(Color.WHITE); // Sets the background color to white
        myFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY)); // Sets a Light Grey border
        myFrame.pack(); // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
        myFrame.setVisible(true); // Sets the Frame to visable
    }
}