package com.demo.service.impl;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.demo.service.WriteToFile;

@Service
public class WriteToFileImpl implements WriteToFile {

    @Override
    public void writeSourceCode(String directory, String fileName, String sourceCode) {
        try {
            FileWriter fileWriter = new FileWriter(directory + fileName);
            fileWriter.write(sourceCode);
            fileWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
