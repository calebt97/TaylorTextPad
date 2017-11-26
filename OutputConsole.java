/*Creates the console to display the output of java code that has been ran*/


 import javax.swing.*;
import javax.swing.event.*;
 import javax.swing.text.SimpleAttributeSet;
 import javax.swing.text.StyledDocument;
 import javax.swing.text.StyleConstants;
import java.awt.*;
 import java.awt.event.WindowEvent;
 import java.awt.event.WindowListener;

 public class OutputConsole extends JFrame {
    JFrame consoleFrame;
    JTextPane console;
    private boolean doesExist = false;
    StyledDocument doc;
    SimpleAttributeSet keyWord;
    //no arg constructor, does nothing.
    public OutputConsole(){
    }

    //builds console and
    public void build(){
        consoleFrame = new JFrame();
        buildConsoleListener();
        buildFrame();
        console = new JTextPane();
        doc = console.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.DARK_GRAY);
        StyleConstants.setBackground(keyWord, Color.WHITE);
        console.setText(" ");
        consoleFrame.add(console);
    }

    //appends Text
    public void append(String message){
        try {
            doc.insertString(doc.getLength(), message, keyWord);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean doesExist(){
        return doesExist;
    }

    public void makeVisible(boolean x){

        consoleFrame.setVisible(x);
    }

    //builds frame
    private void buildFrame(){
        consoleFrame.setSize(800,300);
        consoleFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        consoleFrame.setLocationRelativeTo(null);
        consoleFrame.setResizable(true);
        consoleFrame.setVisible(true);
    }


    private void buildConsoleListener(){
        consoleFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                doesExist = true;
            }
            @Override
            public void windowClosing(WindowEvent e) {
            }
            @Override
            public void windowClosed(WindowEvent e) {
                doesExist = false;
            }
            @Override
            public void windowIconified(WindowEvent e) {
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
            }
            @Override
            public void windowActivated(WindowEvent e) {
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

    }
}
