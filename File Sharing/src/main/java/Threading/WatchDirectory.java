/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threading;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import Files.FileDownload;
import javafx.scene.control.ListView;
import MainApp.FXMLDocumentController;
import javafx.application.Platform;

import ProjectInterface.FoldermonitorImpl;
import ProjectInterface.LocalFiles;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sean O Connor
 */
public class WatchDirectory implements Runnable {

    FoldermonitorImpl fimpl = new FoldermonitorImpl();
    LocalFiles lf = new LocalFiles();
    private boolean res = false;

    public static ListView lv;

    FileDownload fd = FileDownload.getInstance();
    directoryThread dth = new directoryThread();
//  FXMLDocumentController fx = new FXMLDocumentController (); 

    public boolean watchLocal() throws IOException, InterruptedException {
        for (;;) {

            Path myDir = Paths.get(fd.getPath());

            try {
                WatchService watcher = myDir.getFileSystem().newWatchService();
                myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

                WatchKey watckKey = watcher.take();

                List<WatchEvent<?>> events = watckKey.pollEvents();
                for (WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        res = true;
                        lf.isChange(res);
                        System.out.println("Created: " + event.context().toString());

                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        res = true;
                        lf.isChange(res);
                        System.out.println("Delete: " + event.context().toString());

                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        res = true;
                        lf.isChange(res);

                        System.out.println("Modify: " + event.context().toString());
                    } else {
                        res = false;
                        lf.isChange(res);

                    }

                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Error: " + e.toString());
            }
            return res;
        }

    }

    public boolean watchShared() throws IOException, InterruptedException {
        for (;;) {

            Path myDir = Paths.get(fimpl.getFilePath());

            try {
                WatchService watcher = myDir.getFileSystem().newWatchService();
                myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

                WatchKey watckKey = watcher.take();

                List<WatchEvent<?>> events = watckKey.pollEvents();
                for (WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        res = true;
                        fimpl.isChange(res);
                        System.out.println("Created: " + event.context().toString());

                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        res = true;
                        fimpl.isChange(res);
                        System.out.println("Delete: " + event.context().toString());

                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        res = true;
                        fimpl.isChange(res);

                        System.out.println("Modify: " + event.context().toString());
                    } else {
                        res = false;
                        fimpl.isChange(res);

                    }

                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Error: " + e.toString());
            }
            return res;
        }

    }

    @Override
    public void run() {
        for (;;) {
            try {

                lf.isChange(watchLocal());
                fimpl.isChange(watchShared());
            } catch (IOException ex) {
                Logger.getLogger(WatchDirectory.class.getName()).log(Level.SEVERE, null, ex);

            } catch (InterruptedException ex) {
                Logger.getLogger(WatchDirectory.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    /**
     * @return the res
     */
    public boolean isRes() {
        return res;
    }

    /**
     * @param res the res to set
     */
    public void setRes(boolean res) {
        this.res = res;
    }
}
