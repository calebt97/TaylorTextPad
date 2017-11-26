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

/* This class creates the textpad, allows for saving, opening and editing files. */


public class Main extends JFrame {

   public JFrame frame;
    JTextArea text;
    JMenuItem saveAs;
    JMenuItem save;
    JPanel buttonsettings;
    JTextField size;
    boolean sameOpen = false; //will enable "Save function" if set to true later on
    String openedName;
    JMenuItem quit;
    FontBuilder fb;
    JMenuItem fileDirectory;
    String savePath;
    JButton run;
    String fileName;
    JLabel fontLabel;
    JMenuBar optionsMenu;
    JComboBox font;
    JLabel sizeLabel;
    File f;

    public Main() throws IOException{

        //defaults font
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

        fileName = chooser.getSelectedFile().getName();
        f = chooser.getSelectedFile();
        System.out.println(fileName);
        savePath = chooser.getCurrentDirectory().getCanonicalPath();
        System.out.println(savePath);
        if (k == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(chooser.getSelectedFile());
                fw.write(text.getText());
                fw.close();
                sameOpen = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }}
    /* Save method if the file has been previously saved, determined by sameOpen variable*/

    public void saveFile(String k)throws IOException{

        File file = new File(savePath);
        FileWriter inFile = new FileWriter(file);
        inFile.write(text.getText());
        inFile.close();
        JOptionPane.showMessageDialog(null,"Saved");


    }

    //Allows for various actions as the
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
                        saveFile(savePath);
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
                f = chooser.getSelectedFile();
                text.setText("");
                Scanner inFile = new Scanner(f);
                while (inFile.hasNextLine())
                    text.append(inFile.nextLine());
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
                        saveFile(openedName);
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
                        saveFile(savePath);
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
                    new CompileAndRun(fileName,savePath);
                    System.out.println("Completed");

                }
                catch(Exception i){
                    i.printStackTrace();
                }
            }
        });
    }

//Adds the top bar to the textpad
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

//adds the final touches to the frame.
    public void finishUp(){
        text = new JTextArea();
        text.setFont(new Font(fb.getStyle(),fb.getPLAIN(),fb.getSize()));
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        frame.setTitle("TaylorTextPad");
        buttonsettings.setSize(JFrame.WIDTH,200);
        frame.add(text);
        frame.add(buttonsettings,BorderLayout.NORTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
