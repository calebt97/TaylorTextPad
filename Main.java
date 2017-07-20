/**
 * Created by calebtaylor on 7/19/2017.
 */



import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
public class Main extends JFrame {
    //k used throughout is an arbitrary string name, used for lack of a better term.
    JFrame frame;
    JTextArea text;
    JButton saveAs;
    JButton save;
    JPanel buttonPanel;
    JButton open;
    boolean sameOpen = false;
    String openedName;
    int counter = 0;
    public Main() throws IOException{

        //creates the GUI
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonPanel = new JPanel();
        //Allows user to open old files
        open = new JButton("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    openFile();
                }
                catch(IOException a){
                    a.printStackTrace();
                }
            }
        });

        //Saves new file
        saveAs = new JButton("Save As");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    saveAsFile();
                }
              catch(IOException a){
                    a.printStackTrace();
              }
            }
        });
        //resaves file that has already been opened or created
        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sameOpen){
                try {
                    saveFile(openedName);
                } catch (IOException a) {
                    a.printStackTrace();
                }

            }
            else{
                    JOptionPane.showMessageDialog(null, "This is" +
                            " an unnamed file, please use the Save As");
                }
            }});

        buttonPanel.add(open);
        buttonPanel.add(saveAs);
        buttonPanel.add(save);
        text = new JTextArea();
        text.setFont(new Font("Arial",Font.PLAIN,16));
        text.setPreferredSize(new Dimension(500,490));
        frame.add(text);
        frame.add(buttonPanel,BorderLayout.NORTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    public static void main(String[] args) throws IOException {
new Main();
    }


    public void saveAsFile()throws IOException{
        sameOpen = true;
        String k = JOptionPane.showInputDialog(null, "Please input the file name");
        openedName = k;
        File file = new File(k);
        FileWriter inFile = new FileWriter(file);
        inFile.write(text.getText());
        inFile.close();
JOptionPane.showMessageDialog(null,"Saved");


    }

    public void openFile() throws IOException{
        sameOpen = true;
        String k = JOptionPane.showInputDialog(null, "Please input the file name");
        openedName = k;
        try{
            File file = new File(k);
            Scanner inFile = new Scanner(file);
            while(inFile.hasNext()){
                text.append(inFile.nextLine());
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveFile(String k)throws IOException{

        File file = new File(k);
        FileWriter inFile = new FileWriter(file);
        inFile.write(text.getText());
        inFile.close();
        JOptionPane.showMessageDialog(null,"Saved");


    }
}
