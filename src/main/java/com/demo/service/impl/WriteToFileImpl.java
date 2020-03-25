package com.demo.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.demo.service.WriteToFile;

@Service
public class WriteToFileImpl implements WriteToFile {

    @Override
    public void writeSourceCode(String sourceCode) {
        try {
            FileWriter fileWriter = new FileWriter("temp/filename.c");
            fileWriter.write(sourceCode);
            fileWriter.close();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
