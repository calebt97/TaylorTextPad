
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/* Attempts to compile/run the text in the file if saved as a .java file. Also builds the console to display the output
 and error messages
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
            int result = compile(fileName);
            System.out.println("javac returned "+result);
            result = run(withoutExtension);
        } catch (Exception ex) {
            System.out.println("This shit didn't work");
            ex.printStackTrace();
        }
    }
//builds another process to run the compiled class
    public int run(String classToBeRan) throws IOException, InterruptedException {
        System.out.println("Starting beginning");
        ProcessBuilder pb = new ProcessBuilder("java ", classToBeRan);
        pb.redirectErrorStream(true);
        pb.directory(new File(savePath));
        System.out.println("Starting process");
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

        int result = p.waitFor();

        consumer.join();

        System.out.println(consumer.getOutput());

        return result;
    }

//builds process to compile the file, once saved as a .java
    public int compile(String file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac ", file);
        //If changed to just pb.redirectError(), hides error messages
        pb.redirectErrorStream(true);


        pb.directory(new File(savePath));
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

        int result = p.waitFor();
        consumer.join();
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