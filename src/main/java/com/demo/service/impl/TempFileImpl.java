package com.demo.service.impl;

import java.io.File;
import java.io.FileWriter;

import org.springframework.stereotype.Service;

import com.demo.service.TempFile;

@Service
public class TempFileImpl implements TempFile {

    @Override
    public void createFile(String filename) {
        try {
            File fileName = new File("temp/filename.c");
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void deleteFile(String errorTag) {
        
        if (errorTag.equals("error")) {
            File fileNameWithExtension = new File("temp/filename.c");
            if (fileNameWithExtension.delete()) {
//                System.out.println("File deleted successfully with extension!");
            } else {
//                System.out.println("Failed to delete the file!");
            }
            
        } else {
            File fileNameWithExtension = new File("temp/filename.c");
            if (fileNameWithExtension.delete()) {
//                System.out.println("File deleted successfully with extension!");
            } else {
//                System.out.println("Failed to delete the file!");
            }
            
            File fileName = new File("temp/filename");
            if (fileName.delete()) {
//                System.out.println("File deleted successfully!");
            } else {
//                System.out.println("Failed to delete the file!");
            }            
        }
    }

}
