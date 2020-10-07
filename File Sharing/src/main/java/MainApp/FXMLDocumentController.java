package MainApp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import ProjectInterface.FoldermonitorImpl;
import java.io.File;
import java.util.Arrays;
import javafx.scene.control.ListView;
import Files.FileDownload;
import java.io.IOException;
import jcifs.smb.SmbException;

import ProjectInterface.Changes;
import Files.Navigation;
import Files.FileUpload;
import Threading.UploadThread;
import Threading.directoryThread;
import Threading.downloadThread;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;

import ProjectInterface.LocalFiles;
import Threading.WatchDirectory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import Comparing.FolderCompare;

/**
 *
 * @author R00104637
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ListView lstFiles;

    FoldermonitorImpl fimpl = new FoldermonitorImpl();
    FileDownload fd = FileDownload.getInstance();
    LocalFiles lf = new LocalFiles();
    WatchDirectory wd = new WatchDirectory();
    directoryThread dth = new directoryThread();
    FolderCompare fc = new FolderCompare();

    //String selection = lstFiles.getSelectionModel().getSelectedItem().toString();
    @FXML
    private Button btnPlay;

    @FXML
    private Button btnUpload;

    @FXML
    private Button btnChange;

    @FXML
    private void changeText() throws SmbException, IOException, InterruptedException {
        //boolean result = false;

//        String selection = getLstFiles().getSelectionModel().getSelectedItem().toString();
        //fimpl.setOpenFile(selection);
        //fimpl.closeFile(selection);
        //fimpl.openFile(selection);
        //fd.upload(selection);
        //fd.download(selection);
//        fd.DownloadFiles(selection);
//fd.down(selection);
        //downloadThread dt = new downloadThread("Thread1",selection);
        //dt.start();
        //fd.test();
        fimpl.addObserver(fd);
        String folder = "Shared";
        String path = fimpl.getFilePath();
        Navigation nav = new Navigation();
        Changes cd = nav.getDirectory(folder);
        cd.updates(getLstFiles(), path);

        // System.out.println(result);
        //fimpl.newList(lstFiles,"");
    }

    @FXML
    private void uploadFiles() {

        String selection = getLstFiles().getSelectionModel().getSelectedItem().toString();
        UploadThread uptrd = new UploadThread("Thread1", selection);
        uptrd.start();

    }

    @FXML
    private void changeFiles() throws IOException {
//         String selection = getLstFiles().getSelectionModel().getSelectedItem().toString();
        String folder = "User";
        Navigation nav = new Navigation();
        String path = "C:\\Users\\sean1\\Music\\MyMusic";
        Changes cd = nav.getDirectory(folder);
        cd.updates(getLstFiles(), path);
        Path folder1 = Paths.get("C:\\Users\\sean1\\Music\\MyMusic");
        Path folder2 = Paths.get("\\\\LAPTOP-LG9K8F0U\\Shared");
        fc.compareFolders(folder1, folder2, lstFiles);

    }

    public void refresh() {
        Platform.runLater(new Runnable() {

            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
// ...
            @Override
            public void run() {

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

// wd.watch(fd.getPath(), lstFiles);
        String test = "Shared";
        Navigation nav = new Navigation();
        String path = "";
        Changes cd = nav.getDirectory(test);
        cd.updates(getLstFiles(), path);
        WatchDirectory.lv = lstFiles;
        directoryThread.lv = lstFiles;
        FolderCompare.list = lstFiles;
        LocalFiles.list = lstFiles;

        new Thread(wd).start();

    }

    // TODO
    /**
     * @return the lstFiles
     */
    public ListView getLstFiles() {
        return lstFiles;
    }

    /**
     * @param lstFiles the lstFiles to set
     */
    public void setLstFiles(ListView lstFiles) {
        this.lstFiles = lstFiles;
    }

}
