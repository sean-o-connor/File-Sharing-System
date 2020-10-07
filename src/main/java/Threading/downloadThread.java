/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import Files.FileDownload;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.SmbException;

public class downloadThread implements Runnable {

    private Thread t;
    private String threadName;
    private String selection;

    FileDownload df = FileDownload.getInstance();

    public downloadThread(String name, String selection) {
        this.selection = selection;
        this.threadName = name;

    }

    @Override
    public void run() {
        try {

            df.down(selection);
            Thread.sleep(50);

        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        } catch (MalformedURLException ex) {
            Logger.getLogger(downloadThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SmbException ex) {
            Logger.getLogger(downloadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

}
