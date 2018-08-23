
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/* Attempts to compile/run the text in the file if saved as a .java file. Also appends to the OutputConsole
to display the output
 and error messages.
 */
class CompileAndRun {

    /*
    fileName is the entire file name, "main.java" for example.
    withoutExtension is the file without .java "main".
    When running the code through processbuilder, I must use the fileName for compilation,
    and withoutExtension for executing the code.
    Similar to "javac main.java" then "java main"
    in the command line. savePath is the entire file path needed to find the exact file to be ran.
    */
 
    private final String fileName;
    private final String withoutExtension;
    private final OutputConsole console;
    private final String savePath;


    public CompileAndRun(String fileName, String savePath) {
     /*OutputConsole is where the output is shown. The object can be made invisible by the user if they desire,
     which is reversed at compilation if neccessary.
     */
        console = new OutputConsole();
        console.build();
        this.savePath = savePath;
        this.fileName = fileName;

        String[] tokens = fileName.split("\\.");
        withoutExtension = tokens[0];
    }
 /*
 Calls the various methods required to compile then run. The try-catch block is there for my uses, seeing why/if various things fail.
 */
    public void runAsJava(){
        try {
            compile(fileName);
            run(withoutExtension);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
  

    //builds another process to run the compiled class
    private void run(String classToBeRan) throws IOException, InterruptedException {
    
        ProcessBuilder pb = new ProcessBuilder("java ", classToBeRan);
        pb.redirectErrorStream(true);
     
     //The full path taken in during construction of the object is used here.
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


    //builds process to compile the file, once it has been saved as a .java
    private void compile(String file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac ", file);
        //If changed to just pb.redirectError(), hides error messages
        pb.redirectErrorStream(true);

     //The full path taken in during construction of the object is used here.
        pb.directory(new File(savePath));
        Process p = pb.start();
     
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

         p.waitFor();
        consumer.join();
        //appends
        buildCompileConsole(consumer.getOutput().toString());

    }
/*This method only makes the console visible if it isn't already during compilation. Then appends input.
 Which is why there is no need for it to be called a second time during the run method*/
    private void buildCompileConsole(String input){

        if(!console.doesExist())
            console.makeVisible(true);

        console.append(input);
    }
 
    class InputStreamConsumer extends Thread {

        private final InputStream iStream;
        private Exception exp;
        private StringBuilder output;

        InputStreamConsumer(InputStream iStream) {
            this.iStream = iStream;
        }

        @Override
        public void run() {
            int in;
            output = new StringBuilder(64);
            try {
                while ((in = iStream.read()) != -1) {
                    output.append((char) in);
                }
            } catch (Exception e) {
                e.printStackTrace();
                exp = e;
            }
        }

        StringBuilder getOutput() {
            return output;
        }

        public Exception getException() {
            return exp;
        }
    }
}
