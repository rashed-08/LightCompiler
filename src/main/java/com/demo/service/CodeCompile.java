package com.demo.service;

import com.demo.model.Solution;

public interface CodeCompile {
    
    void createFile(String filename);
    void writeSourceCode(String sourceCode);
    int compileCode(final Solution solution);
    String getSTDIN(Solution solution);
    String executeCode();
    void deleteFile();
}
