/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Files.Navigation;
import ProjectInterface.Changes;
import javafx.scene.control.ListView;

import ProjectInterface.FoldermonitorImpl;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sean O Connor
 */
public class directoryThread implements Runnable {

    private String currentPath;
    public static ListView lv;

    private Thread t;
    private String threadName;
    private String selection;

    @Override
    public void run() {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        try {
            Runnable task = () -> {
                String test = "";
                Navigation nav = new Navigation();
                String path;

                test = "SHARED";
                Changes cd = nav.getDirectory(test);
                currentPath = "C:\\Users\\sean1\\Music\\Shared";
                cd.updates(lv, currentPath);

                System.out.println("Executing Task At " + System.nanoTime());
            };

            scheduledExecutorService.schedule(task, 1, TimeUnit.SECONDS);
            scheduledExecutorService.awaitTermination(2, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {
            Logger.getLogger(directoryThread.class.getName()).log(Level.SEVERE, null, ex);
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
