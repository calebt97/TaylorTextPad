
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/* Attempts to compile/run the text in the file if saved as a .java file. It uses "javac blank.java" and "java  blank" to
   do that. Output/Error messages are printed out to the developer console. But the next update will build a console within the text
   pad. 
 */
public class CompileAndRun {
    String savePath;
    String fileName;
    String withoutExtension;
    public CompileAndRun(String fileName, String savePath) {

        this.fileName = fileName;
        this.savePath = savePath;
        String[] tokens = fileName.split("\\.");
        withoutExtension = tokens[0];
        try {
             compile(fileName);
             run(withoutExtension);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//builds another process to run the compiled class
    public int run(String classToBeRan) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java ", classToBeRan);
        pb.redirectErrorStream(true);
        //Sets the directory.
        pb.directory(new File(savePath));
        //Starts the process, and appends 
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();
        //waits for the process to complete
        int result = p.waitFor();

        consumer.join();
        //Prints the results from the process
        System.out.println(consumer.getOutput());

        return result;
    }

//builds process to compile the file, once saved as a .java
    public int compile(String file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac ", file);
        //Allows me to get the error messages to be printed out.
        pb.redirectErrorStream(true);
        //Sets the directory for the process, using the savePath String I received in the constructor.
        pb.directory(new File(savePath));
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();
        //Waits until everything is done.
        int result = p.waitFor();
        consumer.join();
        //Will print out error messages if necessary.
        System.out.println(consumer.getOutput());

        return result;
    }

    public class InputStreamConsumer extends Thread {

        private InputStream is;
        private Exception exp;
        private StringBuilder output;

        public InputStreamConsumer(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            int in = -1;
            output = new StringBuilder(64);
            try {
                while ((in = is.read()) != -1) {
                    output.append((char) in);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                exp = ex;
            }
        }

        public StringBuilder getOutput() {
            return output;
        }

        public Exception getException() {
            return exp;
        }
    }
}
