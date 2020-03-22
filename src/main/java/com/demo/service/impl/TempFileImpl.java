package com.demo.service.impl;

import java.io.File;
import java.io.FileWriter;

import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.TempFile;

@Service
public class TempFileImpl implements TempFile {

    @Override
    public void createFile(String filename) {
        try {
            File fileName = new File("temp/filename.c");
            FileWriter fileWriter = new FileWriter(fileName);
            System.out.println("File Saved Successfully!");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred!");
        }
        
    }

    @Override
    public void deleteFile() {
        File fileName = new File("temp/filename.c");
        if (fileName.delete()) {
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("Failed to delete the file!");
        }
    }

}
