/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparing;

import Threading.UploadThread;
import Threading.downloadThread;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import Threading.directoryThread;
import javafx.application.Platform;
import ProjectInterface.LocalFiles;

/**
 *
 * @author Sean O Connor
 */
public class FolderCompare {

    public static ListView list;
    LocalFiles lf  = new LocalFiles();

    public boolean compareFolders(final Path pathOne, final Path pathSecond, ListView lv) throws IOException {

        final TreeSet<String> treeOne = new TreeSet();
        Files.walkFileTree(pathOne, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relPath = pathOne.relativize(file);
                String entry = relPath.toString();
                treeOne.add(entry);
                return FileVisitResult.CONTINUE;
            }
        });

        final TreeSet<String> treeSecond = new TreeSet();
        Files.walkFileTree(pathSecond, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relPath = pathSecond.relativize(file);
                String entry = relPath.toString();
                treeSecond.add(entry);
                return FileVisitResult.CONTINUE;
            }
        });
        ArrayList<String> folder1 = new ArrayList<String>();
        folder1.addAll(treeOne);

        ArrayList<String> folder2 = new ArrayList<String>();
        folder2.addAll(treeSecond);

        addButtons(folder1, folder2, lv);
        return treeOne.equals(treeSecond);
    }

    public void addButtons(ArrayList<String> a1, ArrayList<String> a2, ListView lv) {

        int counter = 0;
        int listItem = 0;
        int count = 0;

        List<Button> upload = new ArrayList<>();
        List<Button> download = new ArrayList<>();
        List<Button> play = new ArrayList<>();
        ArrayList<String> alBoth = new ArrayList<>();
        ArrayList<String> alLocal = new ArrayList<>();
        ArrayList<String> alShared = new ArrayList<>();
        ArrayList<String> compare = new ArrayList<>();
        lv.getItems();

        while (counter < lv.getItems().size()) {
            System.out.print(listItem);
            String res = lv.getItems().get(listItem).toString();
            System.out.println(res);
            if (a1.contains(res)) {

                if (a2.contains(res)) {
                    System.out.print("SUCCESS");

                    alBoth.add(res);
                    //lv.getItems().addAll((res + " " ),new Button("PLAY"));

                } else {
                    alLocal.add(res);

                }
            }

            counter++;

            listItem++;

            Button playMusic = new Button("PLAY");
        }
        lv.getItems().clear();

        for (int i = 0; i < a2.size(); i++) {
            String res = a2.get(i);
            if (!a1.contains(res)) {
                alShared.add(res);
            }

        }
        for (int i = 0; i < alBoth.size(); i++) {
            Button playMusic = new Button("PLAY");
            lv.getItems().addAll(alBoth.get(i), playMusic);
            playMusic.setOnAction((ActionEvent event) -> {
                System.out.println("clicked");
            });
        }

        for (int i = 0; i < alLocal.size(); i++) {
            Button playMusic = new Button("PLAY");
            Button uploadMusic = new Button("UPLOAD");
            lv.getItems().addAll(alLocal.get(i), playMusic, uploadMusic);
            playMusic.setOnAction((ActionEvent event) -> {
                //lf.openFile("");
                
                System.out.println("playing");
                
            });
             
            uploadMusic.setOnAction((ActionEvent event) -> {
                String selection = list.getSelectionModel().getSelectedItem().toString();
                UploadThread uptrd = new UploadThread("Thread1", selection);
                uptrd.start();
                System.out.println("upload");
            });
        }
        final int test = alShared.size() - 1;
        int i = 0;
        final int get = i;
        for (i = 0; i < alShared.size(); i++) {
            Button downloadMusic = new Button("DOWNLOAD");
            lv.getItems().addAll(alShared.get(i), downloadMusic);
            downloadMusic.setOnAction((ActionEvent event) -> {
                String selection = list.getSelectionModel().getSelectedItem().toString();
                downloadThread dt = new downloadThread("Thread1", selection);
                dt.start();
                directoryThread dirt = new directoryThread();
                Platform.runLater(dirt);
                System.out.println("download");
            });

        }

    }
}
