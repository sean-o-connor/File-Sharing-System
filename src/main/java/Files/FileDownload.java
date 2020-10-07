/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import ProjectInterface.FoldermonitorImpl;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFileInputStream;
import ProjectInterface.Changes;
import java.util.Observable;
import javafx.scene.control.ListView;

/**
 *
 * @author sean1
 */
public class FileDownload implements Observer, Changes {

    private static FileDownload fDown = new FileDownload();
    private List<Changes> observers = new ArrayList<Changes>();
    private int state;
    private FoldermonitorImpl fimpl;

    private FileDownload() {

    }

    @Override
    public void update(Observable o, Object arg) {
        fimpl = (FoldermonitorImpl) o;

    }

    public static FileDownload getInstance() {
        return fDown;
    }

    private String user = "Sean O Connor";
    private String pass = "password";
    private String sharedFolder = "Shared";
    private String path = "C:\\Users\\sean1\\Music\\MyMusic";
    private String path2 = "Homegroup\\sean o connor\\Shared";

    
    @Override
    public void updates(ListView lv, String path) {
        path = this.getPath();
        fimpl.newList(lv, path);
    }
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    public void down(String selection) throws MalformedURLException, SmbException {
        InputStream in = null;
        OutputStream out = null;
        try {

            String SambaURL = "smb://" + user + ":" + pass + "@192.168.8.101/" + sharedFolder + "/" + selection;
            File destinationFolder = new File(path);
            File child = new File(destinationFolder + "/" + selection);
            SmbFile fileToGet = new SmbFile(SambaURL);
            fileToGet.connect();

            in = new BufferedInputStream(new SmbFileInputStream(fileToGet));
            out = new BufferedOutputStream(new FileOutputStream(child));

            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            String msg = "ERROR " + e.getLocalizedMessage();
            System.out.println(msg);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }

        }
    }

    /**
     * @return the path2
     */
    public String getPath2() {
        return path2;
    }

    /**
     * @param path2 the path2 to set
     */
    public void setPath2(String path2) {
        this.path2 = path2;
    }
}
