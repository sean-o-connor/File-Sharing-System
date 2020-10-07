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
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import Threading.UploadThread;

/**
 *
 * @author sean1
 */
public class FileUpload {

    private String user = "Sean O Connor";
    private String pass = "password";
    private String sharedFolder = "Shared";
    private String path = "C:\\Users\\sean1\\Music\\MyMusic\\";

    public void upload(String selection) {

        InputStream in = null;
        OutputStream out = null;

        try {
            //Get a picture

            //File localFile = new File("C:\\Users\\sean1\\Music\\MyMusic\\Yes.txt");
            File localFile = new File(path.concat(selection.toString()));
            //System.out.print(path+selection);
            String path = "smb://" + getUser() + ":" + getPass() + "@192.168.8.101/" + getSharedFolder() + "/"; //The shared directory to store pictures
            SmbFile remoteFile = new SmbFile(path + localFile.getName());
            //Try to connect

            //String path = "";
            // path = "smb://192.168.8.100/"+sharedFolder+"/";
            //NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
            //SmbFile remoteFile = new SmbFile(path,auth);
            remoteFile.connect();

            in = new BufferedInputStream(new FileInputStream(localFile));
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));

            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            String msg = "The error occurred: " + e.getLocalizedMessage();
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
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the sharedFolder
     */
    public String getSharedFolder() {
        return sharedFolder;
    }

    /**
     * @param sharedFolder the sharedFolder to set
     */
    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}
