package com.demo.service;

public interface TempFile {

     String getDirectory();
     void createFile(String directory, String fileName);
     void deleteFile(String directory, String fileName, String errorTag);
}
