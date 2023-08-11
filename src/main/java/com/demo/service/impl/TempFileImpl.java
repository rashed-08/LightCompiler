package com.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import com.demo.service.TempFile;

@Service
public class TempFileImpl implements TempFile {

    @Override
    public void createFile(String directory, String filename) {
        try {
            File fileName = new File(directory + File.separator + filename);
            boolean fileCreatedSuccessful = fileName.createNewFile();
            if (!fileCreatedSuccessful) {
                System.out.println("Directory: " + directory + ", file: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteFile(String fileName) {
        File file = new File(fileName);
        boolean success = file.delete();
        if (success) System.out.println("Deleted file: " + fileName);
        else System.out.println("Could not delete");
    }

    @Override
    public void deleteFiles(String directory, String fileName, String errorTag) {

        try {
            File cleanFiles = new File(directory);
            FileUtils.cleanDirectory(cleanFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public String getDirectory() {
        Path homeDirectory = Paths.get(System.getProperty("user.home"));
        Path subDirectory = Paths.get(homeDirectory.toString(),File.separator + "temp");
        File createDirectory = new File(subDirectory.toString());
        File directory = new File(createDirectory.toString());
        if (!directory.exists()) {
            if (directory.mkdirs()) {
            } 
        }
        return createDirectory.toString();
    }
}
