/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectInterface;

import MainApp.FXMLDocumentController;
import java.io.File;
import java.util.ArrayList;

import java.awt.Desktop;

import MainApp.FXMLDocumentController;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import Files.FileDownload;
import Files.Observer;
import java.util.Observable;
import javafx.scene.control.ListView;

import Threading.directoryThread;

/**
 *
 * @author sean1
 */
public class FoldermonitorImpl extends Observable implements FolderMonitor, Changes {

    private int counter = 0;
    private String[] names;
    private String filePath = "C:\\Users\\sean1\\Music\\Shared\\";
    private String openFile;
    private Scanner scan;

    directoryThread dth = new directoryThread();

    public FoldermonitorImpl() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEOF() {

        try {
            scan = new Scanner(new File(getFilePath() + openFile));
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
        openFile = name;
        File fileIn = null;
        String playFile = null;
        try {
            fileIn = new File(getFilePath() + openFile);
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
        byte by = 0;
        byte[] data2 = null;
        byte counter = 0;
        Path path = Paths.get(getFilePath(), openFile);
        if (isEOF() == false) {
            try {

                byte[] data = Files.readAllBytes(path);

                for (int i = 0; i < data.length; i++) {

                    //  System.out.print((char) data[i]);
                    by += data[i];
                    isEOF();

                }
                return by;
            } catch (IOException ex) {
                Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

        /*
   String pathToFile = filePath+openFile;
File myOpenFile = new File(pathToFile);   // open a file
DataInputStream dataInputStream = null;
     try {
         dataInputStream = new DataInputStream(new FileInputStream(myOpenFile)); // create a data input stream pointed to open file
     } catch (FileNotFoundException ex) {
         Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);
     }
     try { 
         int myChar = dataInputStream.read();
         System.out.print(myChar);
     } catch (IOException ex) {
         Logger.getLogger(FoldermonitorImpl.class.getName()).log(Level.SEVERE, null, ex);
     }*/
        return by;
    }

    @Override
    public boolean closeFile(String name) {
        boolean closed = false;
        openFile = name;
        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(getFilePath() + getOpenFile());

            fos.close();
            File fileIn = new File(getFilePath() + openFile);
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newList(ListView lv, String path) {
        //path = this.filePath;

    }

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

    public static void playSound(String fileName) {
        Media m = new Media(fileName);
        MediaPlayer player = new MediaPlayer(m);
        player.play();
    }

    public static void stopSound(String fileName) {
        Media m = new Media(fileName);
        MediaPlayer player = new MediaPlayer(m);
        if (player.getStatus() == PLAYING) {
            player.stop();
        }
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

}
