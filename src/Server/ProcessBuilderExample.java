package Server;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ProcessBuilderExample {

    StringBuilder stderr;
    StringBuilder stdout;
    File file;
//  
//  public static void main(String[] args) throws Exception
//  {
//    new ProcessBuilderExample();
//  }

    // can run basic ls or ps commands
    // can run command pipelines
    public ProcessBuilderExample() {
        file = new File("e:");
        stdout = new StringBuilder();
        stderr = new StringBuilder();
    }

    // can run sudo command if you know the password is correct
    public String excute(String s) throws IOException, InterruptedException {
        String[] s1 = s.split("\\s");
        if (s1[0].equals("cd")) {
            s = s + " " + "&cd";
        }
        // build the system command we want to run
        List<String> commands = new ArrayList<String>();
        commands.add("cmd");
        commands.add("/c");
        for (String str : s.split("\\s")) {
            commands.add(str);
        };

//commands.add("move");
//        commands.add("C:\\Users\\T430\\Desktop");
//    commands.add("f:");
//        System.err.println(commands.get(2));
//    commands.add("3");
//    commands.add("8.8.8.8");
// execute the command
        SystemCommandExecutor commandExecutor;
                   
        commandExecutor = new SystemCommandExecutor(commands, file);
        int result = commandExecutor.executeCommand();

// get the stdout and stderr from the command that was run
//        System.out.println("1");
        stdout = commandExecutor.getStandardOutputFromCommand();
        stderr = commandExecutor.getStandardErrorFromCommand();

// print the stdout and stderr
        System.out.println("The numeric result of the command was: " + result);
        System.out.println("STDOUT:");
        System.out.println(stdout);
        System.out.println(stdout.toString().length());
        System.out.println("STDERR:");
        System.out.println(stderr);
        if (s1[0].equals("cd")) {
            stdout.setLength(stdout.length() - 1);
            this.file = new File(stdout.toString());

        }
        if (!stdout.toString().equals("")&&stderr.toString().equals("")) {
            return stdout.toString();
        }
        return stderr.toString();
    }

}
