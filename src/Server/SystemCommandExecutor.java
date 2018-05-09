package Server;

import java.io.*;
import java.util.List;

public class SystemCommandExecutor {

    private List<String> commandInformation;
    private String adminPassword;
    private ThreadedStreamHandler inputStreamHandler;
    private ThreadedStreamHandler errorStreamHandler;
    File file;

    public SystemCommandExecutor(final List<String> commandInformation, File file) {
        if (commandInformation == null) {
            throw new NullPointerException("The commandInformation is required.");
        }
        this.commandInformation = commandInformation;
        this.adminPassword = null;
        this.file = file;
    }

    public int executeCommand()
            throws IOException, InterruptedException {
        int exitValue = -99;

        try {
            ProcessBuilder pb = new ProcessBuilder(commandInformation);
//            File file = new File("c:\\xampp");
            pb.directory(file);
            System.out.println("d:" + file.isFile());
            System.out.println("1");
//            File f;
//            f = pb.directory();
//            System.out.println(f.getAbsolutePath());
            Process process = pb.start();
            process.waitFor();

            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            inputStreamHandler = new ThreadedStreamHandler(inputStream);
            errorStreamHandler = new ThreadedStreamHandler(errorStream);

//            System.out.println(System.getProperty("user.dir"));
            inputStreamHandler.start();
            errorStreamHandler.start();

            exitValue = process.waitFor();

            inputStreamHandler.join();
            errorStreamHandler.join();

        } catch (IOException e) {

            throw e;
        } catch (InterruptedException e) {

            throw e;
        } finally {
            return exitValue;
        }
    }

    /**
     * Get the standard output (stdout) from the command you just exec'd.
     */
    public StringBuilder getStandardOutputFromCommand() {
        try {
            return inputStreamHandler.getOutputBuffer();
        } catch (Exception e) {
            return new StringBuilder("Check your command");
        }
    }

    /**
     * Get the standard error (stderr) from the command you just exec'd.
     */
    public StringBuilder getStandardErrorFromCommand() {
        try {
            return errorStreamHandler.getOutputBuffer();
        } catch (Exception e) {

            return new StringBuilder();
        }
    }

}
