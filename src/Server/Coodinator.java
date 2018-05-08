/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T430
 */
public class Coodinator {
    Semaphore s;
    public Coodinator() {
       s = new Semaphore(1, true);
    } 
    public void accquire(){
        try { 
            s.acquire();
            System.out.println("A thread accquired semaphore");
        } catch (InterruptedException ex) {
            Logger.getLogger(Coodinator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void release(){
        s.release();
        System.out.println("A thread release semaphore");
    }


}
