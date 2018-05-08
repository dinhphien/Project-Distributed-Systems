/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.net.Socket;

/**
 *
 * @author T430
 */
public class Command {

    Socket socket;
    String command;

    public Command(Socket s, String command) {
        this.socket = s;
        this.command = command;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket s) {
        this.socket = s;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
