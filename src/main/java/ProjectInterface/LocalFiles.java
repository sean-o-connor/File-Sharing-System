/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectInterface;

import MainApp.FXMLDocumentController;
import static ProjectInterface.FoldermonitorImpl.stopSound;
import Threading.directoryThread;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListView;

import Threading.directoryThread;
import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author R00104637
 */
public class LocalFiles extends Observable implements FolderMonitor, Changes {

    /**
     * @return the openFile
     */
    public String getOpenFile() {
        return openFile;
    }

    /**
     * @param openFile the openFile to set
     */
    public void setOpenFile(String openFile) {
        this.openFile = openFile;
    }

    private int counter = 0;
    private String[] names;
    private String filePath = "C:\\Users\\sean1\\Music\\MyMusic";
    private String openFile;
    private Scanner scan;
    public static ListView list;

    directoryThread dth = new directoryThread();

    public LocalFiles() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEOF() {
   String name = list.getSelectionModel().getSelectedItem().toString();
        setOpenFile(name);
        try {
            scan = new Scanner(new File(getFilePath() + getOpenFile()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (scan.hasNext()) {

            return false;

        }

        return true;
    }

    @Override
    public String[] getNames(String path) {
        names = null;
        counter = 0;

        File music = new File(path); //getting the notes dir
        names = music.list();

        for (File file : music.listFiles()) { //iterating the files in the dir
            names[counter] = file.getName();
            // System.out.println(names[counter]);
            counter++;

        }
        return names;
    }

    @Override
    public boolean openFile(String name) {
        name = list.getSelectionModel().getSelectedItem().toString();
        setOpenFile(name);
        File fileIn = null;
        String playFile = null;
        try {
            fileIn = new File(getFilePath() + getOpenFile());
            playFile = fileIn.toURI().toURL().toString();
            //playSound(playFile);

            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported");
                return false;
            }

        } catch (IOException ex) {
            Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        getB();
        return true;
    }

    @Override
    public byte getB() {
          String name = list.getSelectionModel().getSelectedItem().toString();
        setOpenFile(name);
        byte by = 0;
        byte[] data2 = null;
        byte counter = 0;
        Path path = Paths.get(getFilePath(), getOpenFile());
        if (isEOF() == false) {
            try {

                byte[] data = Files.readAllBytes(path);

                for (int i = 0; i < data.length; i++) {

                    //  System.out.print((char) data[i]);
                    by += data[i];
                    isEOF();

                }

            } catch (IOException ex) {
                Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return by;
    }

    @Override
    public boolean closeFile(String name) {
          name = list.getSelectionModel().getSelectedItem().toString();
        
        boolean closed = false;
        setOpenFile(name);
        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(getFilePath() + getOpenFile());

            fos.close();
            File fileIn = new File(getFilePath() + getOpenFile());
            String playFile;

            playFile = fileIn.toURI().toURL().toString();

            stopSound(playFile);
            closed = true;

        } catch (Exception ex) {

            System.out.print("IOException: File output stream is closed");
        } finally {

            if (fos != null) {
                try {
                    fos.close();

                    closed = true;
                } catch (IOException ex) {
                    Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return closed;
    }

    @Override
    public boolean isChange(boolean b) {

        if (b == true) {
            setChanged();
            notifyObservers();
            System.out.print("CHANGES MADE TO FOLDER");
            new Thread(dth).start();

        }
        return b;

    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void updates(ListView lv, String path) {
        path = this.filePath;
        setChanged();
        notifyObservers();
        lv.getItems().clear();
        String names[] = getNames(path);
        for (String name : names) {
            lv.getItems().add(name);

        }
    }

}
