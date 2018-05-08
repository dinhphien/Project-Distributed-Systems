/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.NativeString;

/**
 *
 * @author T430
 */
public class CommandExcutor {

    ArrayList<String> rootDir;
    ProcessBuilderExample proc;

    public CommandExcutor() {
        this.rootDir = new ArrayList<>();
        rootDir.add("f://");
        proc = new ProcessBuilderExample();
    }

    public String excute(String s) {
        try {
            //        String[] s1 = s.split("\\s");
//        if (s1[0].equals("gettime")) {
//            return getTime();
//        }
//        if (s1[0].equals("pwd")) {
//            StringBuilder sb = new StringBuilder();
//            for (String r : rootDir) {
//                sb.append(r);
//            }
//            return sb.toString();
//        }
//        if (s1[0].equals("cd")) {
//            if (s1[1].equals("--")) {
//                rootDir.remove(rootDir.size() - 1);
//
//            } else {
//                rootDir.add(s1[1]+"//");
//            }
//            StringBuilder sb = new StringBuilder();
//            for (String r : rootDir) {
//                sb.append(r);
//            }
//            return sb.toString();
//        }
//        if (s1[0].equals("ls")) {
//            StringBuilder sb = new StringBuilder();
//            for (String r : rootDir) {
//                sb.append(r);
//            }
//            File file = new File(sb.toString());
//            try {
//                return showDir(file);
//            } catch (IOException ex) {
//                Logger.getLogger(CommandExcutor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return "No expected statement";
            return proc.excute(s);
        } catch (IOException ex) {
            Logger.getLogger(CommandExcutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommandExcutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getTime() {
        Date date = new Date();
        return date.toString();
    }

    public String showDir(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {;
            if (files[i].isDirectory()) {
                sb.append(" [Dir]" + files[i].getName() + "\n");
            } else {
                sb.append(files[i].getName() + "\n");
            }
        }
        return sb.toString();
    }
}
