package com.demo.service;

public interface TempFile {

     String getDirectory();
     void createFile(String directory, String fileName);
     void deleteFiles(String directory, String fileName, String errorTag);
     void deleteFile(String fileName);
}
