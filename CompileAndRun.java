
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/* Attempts to compile/run the text in the file if saved as a .java file. Also builds the console to display the output
 and error messages
 */
public class CompileAndRun {

    String fileName;
    String withoutExtension;
    OutputConsole console;
    String savePath;


    public CompileAndRun(String fileName, String savePath) {
        console = new OutputConsole();
        console.build();
        this.savePath = savePath;
        this.fileName = fileName;

        String[] tokens = fileName.split("\\.");
        withoutExtension = tokens[0];
    }
    public void runAsJava(){
        try {
            compile(fileName);
            run(withoutExtension);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void updateFile(String s){
        this.fileName = s;
        String[] tokens = fileName.split("\\.");
        withoutExtension = tokens[0];
    }

    //builds another process to run the compiled class
    public void run(String classToBeRan) throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder("java ", classToBeRan);
        pb.redirectErrorStream(true);
        pb.directory(new File(savePath));

        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();
        //if you want the return value from the compilation, make it "int result = p.waitFor();"
        p.waitFor();
        consumer.join();
        //appends output from running the code
        console.append(consumer.getOutput().toString());

    }


    //builds process to compile the file, once saved as a .java
    public void compile(String file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac ", file);
        //If changed to just pb.redirectError(), hides error messages
        pb.redirectErrorStream(true);

        pb.directory(new File(savePath));
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

         p.waitFor();
        consumer.join();
        //appends
        buildCompileConsole(consumer.getOutput().toString());

    }

    private void buildCompileConsole(String input){

        if(!console.doesExist())
            console.makeVisible(true);

        console.append(input);
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