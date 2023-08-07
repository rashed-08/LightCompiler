package com.demo.service;

import java.util.ArrayList;

import com.demo.model.Solution;

public interface CodeCompile {
    
    String getDirectory();
    int compileCode(final Solution solution);
    String getSTDIN(Solution solution);
    String getLanguage(Solution solution);
    ArrayList<String> executeCode();
    void deleteFile(String errorTag);
}
