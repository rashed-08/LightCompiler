package com.demo.service;

import com.demo.model.Solution;

public interface CodeCompile {
    
    void createFile(String filename);
    String compileCode(final Solution solution);
    void deleteFile();
}
