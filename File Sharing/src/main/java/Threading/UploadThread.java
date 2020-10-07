/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import Files.FileUpload;

/**
 *
 * @author Sean O Connor
 */
public class UploadThread implements Runnable {

    FileUpload fup = new FileUpload();

    private Thread t;
    private String threadName;
    private String selection;

    public UploadThread(String name, String selection) {
        this.selection = selection;
        this.threadName = name;

    }

    @Override
    public void run() {
        try {

            fup.upload((selection));
            Thread.sleep(50);

        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

}
