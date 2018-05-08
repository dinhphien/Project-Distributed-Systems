/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import static Client.Client.secret;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author T430
 */
public class ClientHandler extends Thread {

    static KeyPair keypair;
    static KeyPairGenerator keygen;
    static Coodinator coo;
    public static byte[] secret;
    Socket s = null;
    CommandExcutor comm;

    public ClientHandler(Socket s) {
        this.s = s;
//        this.dis = dis;
//        this.dos = dos;
        comm = new CommandExcutor();
        Server.upCount();
    }

    public static void setCoo(Coodinator coo) {
        ClientHandler.coo = coo;
        System.out.println("abc");
    }

    public void run() {
        try {
//            coo.listen();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream iis = new ObjectInputStream(s.getInputStream());
            dos.writeUTF("Hello welcome");
            try {
                keygen = KeyPairGenerator.getInstance("DH");
                keygen.initialize(512);
                keypair = keygen.generateKeyPair();
            } catch (Exception e) {
                System.out.println(e);
            }
            Key k = (Key) iis.readObject();
            oos.writeObject(keypair.getPublic());
            oos.flush();
            KeyAgreement key = KeyAgreement.getInstance("DH");
            key.init(keypair.getPrivate());
            key.doPhase(k, true);
            secret = key.generateSecret();
            final byte[] shortenedKey = new byte[8];
            System.arraycopy(secret, 0, shortenedKey, 0, shortenedKey.length);
            final SecretKeySpec keySpec = new SecretKeySpec(shortenedKey, "DES");
            final Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, keySpec);
            final Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
            decrypt.init(Cipher.DECRYPT_MODE, keySpec);
            while (true) {
                int i = dis.readInt();
                //System.out.println(i);
                byte[] b = new byte[i];
                dis.read(b);
                String s = new String(decrypt.doFinal(b));
                byte[] secretMessage = null;
                try {
                    coo.accquire();
                    secretMessage = encrypt.doFinal(comm.excute(s).getBytes());
                    coo.release();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(this.getName());
                    coo.release();
                }
                dos.writeInt(secretMessage.length);
                dos.flush();
                dos.write(secretMessage);
                dos.flush();

//                Command c = new Command(s1, s);
//                coo.addToQueue(c);
//                
                if (s.equals("quit")) {
//                    dos.writeUTF("Client: bye!");;
//                    dos.flush();
                    System.out.println("exit");
                    Server.downCount();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
