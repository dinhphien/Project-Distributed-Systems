/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author T430
 */
public class Client {

    public static void main(String args[]) {
        KeyPair keypair = null;
        KeyPairGenerator keygen;
        byte[] secret;

        try {
            Socket s = new Socket("127.0.0.1", 8333);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream iis = new ObjectInputStream(s.getInputStream());
            System.out.println(dis.readUTF());
            try {
                keygen = KeyPairGenerator.getInstance("DH");
                keygen.initialize(512);
                keypair = keygen.generateKeyPair();
            } catch (Exception e) {
                System.out.println(e);
            }
            oos.writeObject(keypair.getPublic());
            oos.flush();
            Key k = (Key) iis.readObject();
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
            System.out.println("Completed connection!");
            Scanner sc = new Scanner(System.in);
            String accept;
            int x = dis.readInt();
            //System.out.println(i);
            byte[] z = new byte[x];
            dis.read(z);
            accept = new String(decrypt.doFinal(z));
            System.out.println(accept);
            if (!accept.equals("ok")) {
                System.exit(0);
            }
            while (true) {
                int i = dis.readInt();
                //System.out.println(i);
                byte[] b = new byte[i];
                dis.read(b);
                String s3 = new String(decrypt.doFinal(b));
                System.out.println(s3);
                String account = sc.nextLine();
                byte[] secretMessage = encrypt.doFinal(account.getBytes());
                dos.writeInt(secretMessage.length);
                dos.flush();
                dos.write(secretMessage);
                dos.flush();
                i = dis.readInt();
                //System.out.println(i);
                b = new byte[i];
                dis.read(b);
                s3 = new String(decrypt.doFinal(b));
                System.out.println(s3);
                String passw = sc.nextLine();
                secretMessage = encrypt.doFinal(passw.getBytes());
                dos.writeInt(secretMessage.length);
                dos.flush();
                dos.write(secretMessage);
                dos.flush();
                i = dis.readInt();
                //System.out.println(i);
                b = new byte[i];
                dis.read(b);
                accept = new String(decrypt.doFinal(b));
                System.out.println(accept);
                if (accept.equals("ok")) {
                    break;
                }
            }

            System.out.println("Log in succesful");
            while (true) {

                System.out.print("-->");
                String s2 = sc.nextLine();
                byte[] secretMessage = encrypt.doFinal(s2.getBytes());
                dos.writeInt(secretMessage.length);
                dos.flush();
                dos.write(secretMessage);
                dos.flush();
                if (s2.equals("quit")) {
                    System.out.println("quit");
                    break;

                }
                int i = dis.readInt();
                //System.out.println(i);
                byte[] b = new byte[i];
                dis.read(b);
                String s3 = new String(decrypt.doFinal(b));
                System.out.println(s3);

            }

        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Co loi xay ra. Stop");
            System.exit(0);
        }
    }
}
