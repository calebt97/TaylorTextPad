/*
Creates the console to display the output of java code that has been ran. The console currently isn't attached to anything.
But once I revamp the GUI, I will attach it to the bottom of the textpad, minimized until the CaR has things to display.
This class works exclusively in conjunction with the CompileAndRun class abbreviation "CaR".
I won't break down each GUI related variable, but I will explain the more complicated parts and logic.
*/


 import javax.swing.*;
 import javax.swing.text.SimpleAttributeSet;
 import javax.swing.text.StyledDocument;
 import javax.swing.text.StyleConstants;
import java.awt.*;
 import java.awt.event.WindowEvent;
 import java.awt.event.WindowListener;

 class OutputConsole extends JFrame {
    private JFrame consoleFrame;
     /*
     Most important variable here. If the user closes the console window, this variable changes.
     That way, the CaR knows to make it visible once again when it has output to show the user.
     Otherwise, it would be appending to a console that no one can see.
     */
    private boolean doesExist = false;
    private StyledDocument doc;
    private SimpleAttributeSet keyWord;
  
    //no arg constructor, does nothing.
    public OutputConsole(){
    }

    //builds console and styles the font.
    public void build(){
        consoleFrame = new JFrame();
        buildConsoleListener();
        buildFrame();
        JTextPane console = new JTextPane();
        doc = console.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.DARK_GRAY);
        StyleConstants.setBackground(keyWord, Color.WHITE);
        console.setText(" ");
        consoleFrame.add(console);
    }

    //appends Text as the CaR creates it.
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
