import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.io.File;
import java.util.Scanner;

public class WordleApp extends JFrame {
    int currentIndex;
    static JTextField[] inputField = new JTextField[30];
    JLabel myLabel;
    int guessNumber = 0;
    private String wordleWord;
    private int letterCount[] = new int[26];
    private int lettersUsed[] = new int[26];
    char alphabet[] = new char[26];

    public static void setErrorMsg(String text) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(text, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        optionPane.setPreferredSize(new Dimension(500, 600));
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true);
    }

    public static void setWinMsg(String text, int seconds) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane("You took " + seconds + " seconds!", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(text);
        optionPane.setPreferredSize(new Dimension(300, 300));
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true);
    }

    public static boolean checkWord(String word) throws FileNotFoundException {

        char letter = Character.toLowerCase(word.charAt(0));
        word = word.toLowerCase();
        Scanner txtscan = new Scanner(new File("Letters\\letter_" + letter + ".txt"));

        while (txtscan.hasNext()) {
            String str = txtscan.next();
            if (str.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public static String generateWordle() throws FileNotFoundException {
        int num = (int)(Math.random() * (5750 - 1 + 1) + 1);
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

    private void resetFocusParams(int currentIndex) {
        int IntValue = (int) Math.round((Math.floor(currentIndex / 5.0)) * 5.0);
        int IntValueMax = IntValue + 5;
        System.out.println(IntValue);

        for (int i = 0; i < 30; i++) {
            if (i >= IntValue && i < IntValueMax) {
                inputField[i].setEditable(true);
            }
        }
    }
    
    
    private WordleApp() throws IOException {
        GridBagConstraints positionConstants;
        int i, j;
        setLayout(new GridBagLayout());
        positionConstants = new GridBagConstraints();
        wordleWord = generateWordle();
        // Initialize the values of the arrays; alphabet to uppercase letters; letterCount and letterUsed to zero

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
                    } else if (inputChar == 10) {
                        if ((currentIndex + 1) % 5 == 0) {
                            actionPerformed();
                        }
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
    }

    public void actionPerformed() {

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
                    // pull thread
                    setWinMsg("You have Won!", seconds);
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

    static int seconds = 0;

    public static Thread timer = new Thread() {
        public void run() {
            while(true) {
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                seconds+=1;
            }
        }
    };
    public static void main(String[] args) throws IOException {
        timer.start();
        WordleApp myFrame = new WordleApp();
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setBackground(Color.WHITE);
        myFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.LIGHT_GRAY));
        myFrame.pack();
        myFrame.setVisible(true);
    };
}
