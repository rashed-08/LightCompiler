package com.demo.service;

import java.util.ArrayList;

import com.demo.model.Solution;

public interface CodeCompile {
    
    String getDirectory();
    void createFile(String directory,String filename);
    void writeSourceCode(String sourceCode);
    void prepare(String languageName, String sourceCode, String directory);
    int compileCode(final Solution solution);
    String getSTDIN(Solution solution);
    String getLanguage(Solution solution);
    String getSourceCode(Solution solution);
    ArrayList<String> executeCode();
    void deleteFile(String errorTag);
}
