/**
 * Created by calebtaylor on 7/19/2017.
 */


import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.lang.*;

/*
NOTE: This entire class will be broken down (it is way to big) and reorganized while building a new GUI with JavaFX.
This class creates the textpad, allows for saving, opening and editing files.
*/


public class Main extends JFrame {
/*The vast majority of these variables are for GUI construction. So I wont comment and explain each one.
However, I will explain the other ones that have to do with logic/etc.
*/
   public JFrame frame;
    JTextArea text;
    JMenuItem saveAs;
    JMenuItem save;
    JPanel buttonsettings;
    JTextField size;
   
   /*
   If the "open" or "save as" methods are called, it sets sameOpen to true. This enables the save function.
   If the save function tried to run without either of the other two being called first, it wouldn't have the full
   filepath to save the file
   */
    boolean sameOpen = false;  
    JMenuItem quit;
    FontBuilder fb;
    JMenuItem fileDirectory;
   
   /*
   These two work hand and hand, both used for saving and running files. Used in constructor of CompileAndRun 
   */
    String savePath;
    String fileName;
    JButton run;
    JLabel fontLabel;
    JMenuBar optionsMenu;
    JComboBox font;
    JLabel sizeLabel;
   
   //Works in conjunction with sameOpen boolean variable.
    static File toBeSaved;
    CompileAndRun CandR;
   
   //Every 25 times the a key is pressed, the windowlistener will call the "save" method if the sameOpen boolean variable is also true.
    int timesKeyPressed = 0;

    public Main() throws IOException{

        //defaults font, initializes object for potentially further use.
        fb = new FontBuilder();

        //creates the GUI, *note to increase size to full screen remove resizable clause
        frame = new JFrame();
        frame.setSize(800,800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //builds window listener, gives option to save on close, or open a file upon running program
        buildWindowListener();
        buttonsettings = new JPanel();

        //Builds run button, If clicked and file is saved, attempts to run as Java file
        run = new JButton("Run");
        buildRunButton();

        //Saves new file
        saveAs = new JMenuItem("Save As");
        buildSaveAs();

        //Updates file under current name
        save = new JMenuItem("Save");
        buildSave();

        //Will close/exit program. Will also Autosave changes
        quit = new JMenuItem("Quit");
        buildQuit();

        //opens directory/picks file to open
        fileDirectory = new JMenuItem("Open File");
        fileDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }});

        //allows change in font style, done with drop down box. with label
        fontLabel = new JLabel("Font");
        font = buildFontLabel();

        //changes font size, no need to confirm with button, just does as number changes
        //defaults to 20, if number is cleared, defaults back to 20
        sizeLabel = new JLabel("Font Size");
        size = new JTextField(4);
        buildSize();

         optionsMenu = new JMenuBar();
        JMenu list = new JMenu("Options");

        //creates drop down menu, adds to menu, then panel
        list.add(fileDirectory);
        list.add(saveAs);
        list.add(save);
        list.add(quit);
        optionsMenu.add(list);

        buildButtonSettings();
        //sets default font/area size
        finishUp();

    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    /*If this is the first time a file is being saved, it goes to this method.
     That is determined by the status of the sameOpen variable
    */
    public void saveAsFile()throws IOException{

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int k = chooser.showSaveDialog(null);
        
        /*
       These 3 variables are used for the CompileAndRun class, as well as "saving",
       so that the computer knows the exact location of the file.
       */
        fileName = chooser.getSelectedFile().getName();
        toBeSaved = chooser.getSelectedFile();
        savePath = chooser.getCurrentDirectory().getCanonicalPath();
       //If the user approves the saving through the JFileChooser button, then it enters this.
        if (k == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(toBeSaved);
                fw.write(text.getText());
                fw.close();
             //Enables the "save" method to be ran  
                sameOpen = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }}

    public void saveFile()throws IOException{
      //Goes to the exact location of the current file, overwrites everything then displays "Saved"
        FileWriter inFile = new FileWriter(toBeSaved);
        inFile.write(text.getText());
        inFile.close();
        JOptionPane.showMessageDialog(null,"Saved");

    }

    //Allows for various actions as the window closes or opens. "Autosaves" the file if sameOpen is true.
    public void buildWindowListener()throws IOException {
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Would you like to open " +
                        "any previous files?");
                if(option == 0){
                    openFile();
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null,"Would you like to save" +
                        " your work?");
                if(option == 0) {
                    try {
                        saveFile();
                        System.out.println("1");
                    } catch (Exception a) {
                        try {
                            saveAsFile();
                            System.out.println("2");
                        } catch (Exception b) {

                        }

                    }
                }
                    frame.dispose();
                    System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent e) {

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

    //Opens the file, wherever it is within the computer
    public void openFile(){

        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("File Directory");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(true);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                savePath = chooser.getCurrentDirectory().getCanonicalPath();
                fileName = chooser.getSelectedFile().getName();
                toBeSaved = chooser.getSelectedFile();
               
                //clears the textpad before appending anything new to it.
                text.setText("");
                Scanner inFile = new Scanner(toBeSaved);
                while (inFile.hasNextLine())
                    text.append(inFile.nextLine());
               //enables the "save" function
                sameOpen = true;
            }
            catch(Exception a){
                a.printStackTrace();
            }
        }
        System.out.println(fileName);
        System.out.println(savePath);
    }

    //Sets font
    public JComboBox buildFontLabel(){
             //A list of possible fonts to be used. The JTextPad font will automatically reflect the changes. 
        String[] fontChoices = new String[]{"Arial", "Times New Roman", "Calibri","Joker"};
        JComboBox font = new JComboBox(fontChoices);
        font.setPreferredSize(new Dimension(75,25));
        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              

                int index = font.getSelectedIndex();
                try{
                    switch(index){
                        case 0: fb.setStyle("Arial");
                            break;
                        case 1: fb.setStyle("Times New Roman");
                            break;
                        case 2: fb.setStyle("Calibri");
                            break;
                        case 3: fb.setStyle("Jokerman");
                            break;

                    }
                }
                catch(Exception error){
                    error.printStackTrace();
                }
               
               //sets the font to the new specifications then repaints the frame so that everything will update. 
                text.setFont(new Font(fb.getStyle(),fb.getPLAIN(),fb.getSize()));
                frame.repaint();

            }
        });
        return font;
    }


    public void buildQuit(){
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you wish to quit?");
                if(option == 0){
                    try {
                        saveFile();
                        System.exit(0);
                    }
                    catch(Exception i){
                        System.exit(0);
                    }
                }

            }
        });
    }


    public void buildSize(){
        size.setText("20");
        size.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(size.getText().isEmpty()) {
                    size.setText("20");
                    fb.setSize(20);
                    text.setFont(new Font(fb.getStyle(), fb.getPLAIN(), fb.getSize()));
                }
            }
        });
        size.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!size.getText().isEmpty()){

                    try{
                        int newFont = Integer.parseInt(size.getText());
                        fb.setSize(newFont);
                        text.setFont(new Font(fb.getStyle(),fb.getPLAIN(),fb.getSize()));
                    }
                    catch (Exception a){
                        JOptionPane.showMessageDialog(null, "Wrong font size input.");
                    }
                }

            }
        });
    }


    public void buildSave(){
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sameOpen){
                    try {
                        saveFile();
                    } catch (IOException a) {
                        a.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "This is" +
                            " an unnamed file, please use the Save As");
                }
            }});
    }


    public void buildSaveAs(){
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
    }


    public void buildRunButton(){
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!sameOpen) {
                    JOptionPane.showMessageDialog(null, "You need to save your file first");
                    try {
                        saveAsFile();
                    }
                    catch(Exception a){a.printStackTrace();}
                }
                try {
                    FileWriter inFile = new FileWriter(toBeSaved);
                    inFile.write(text.getText());
                    inFile.close();
                   CandR = new CompileAndRun(fileName,savePath);
                   CandR.runAsJava();
                    System.out.println("Completed");

                }
                catch(Exception i){
                    i.printStackTrace();
                }
            }
        });
    }


    public void buildButtonSettings(){
        buttonsettings.add(optionsMenu);
        buttonsettings.add(run);
        buttonsettings.add(fontLabel);
        buttonsettings.add(font);
        buttonsettings.add(sizeLabel);
        buttonsettings.add(size);
        //puts them in order
        buttonsettings.setLayout(new FlowLayout(0));

    }

//Completes the GUI construction. Brings everything together and makes the frame visible.
    public void finishUp(){
        text = new JTextArea();
        text.setFont(new Font(fb.getStyle(),fb.getPLAIN(),fb.getSize()));
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
               //Autosave function.
                timesKeyPressed++;
                if (timesKeyPressed >= 25 && sameOpen) {
                    try {
                        FileWriter inFile = new FileWriter(toBeSaved);
                        inFile.write(text.getText());
                        inFile.close();
                        timesKeyPressed = 0;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        frame.setTitle("TaylorTextPad");
        buttonsettings.setSize(JFrame.WIDTH,200);
        frame.add(text);
        frame.add(buttonsettings,BorderLayout.NORTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
