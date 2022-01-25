import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WordleApp extends JFrame implements ActionListener {
    int currentIndex;
    JTextField[] inputField = new JTextField[30];
    JLabel myLabel;

    private WordleApp() throws IOException {
        JButton myButton;
        GridBagConstraints positionConstants;
        int i;
        setLayout(new GridBagLayout());
        positionConstants = new GridBagConstraints();

        for (i = 0; i < 30; ++i) {
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
                    if (inputChar == 8) {
                        if (Math.floorMod(currentIndex,5) != 0) {
                            currentIndex -= 1;
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

        myLabel = new JLabel("word not in databank");
        myLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        positionConstants.gridx = 6;
        positionConstants.gridy = 2;
        add(myLabel, positionConstants);

        myButton = new
        JButton("Enter");

        positionConstants.gridx = 6;
        positionConstants.gridy = 3;
        add(myButton, positionConstants);
    }
    public static void main(String[] args) throws IOException {
        WordleApp myFrame = new WordleApp();

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setPreferredSize(new Dimension(400, 600));
        myFrame.pack();
        myFrame.setVisible(true);
        //code to set the focus
        myFrame.inputField[10].requestFocus();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
