package com.demo.service;

import java.util.ArrayList;

import com.demo.model.Solution;

public interface CodeCompile {
    
    void createFile(String filename);
    void writeSourceCode(String sourceCode);
    int compileCode(final Solution solution);
    String getSTDIN(Solution solution);
    ArrayList<String> executeCode();
    void deleteFile();
}
