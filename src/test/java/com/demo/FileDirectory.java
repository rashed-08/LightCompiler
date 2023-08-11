package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.service.impl.TempFileImpl;

@ExtendWith(value = MockitoExtension.class)
public class FileDirectory {
    
    @InjectMocks
    private TempFileImpl tempFile;
    
    @Test
    @DisplayName("Test 1")
    public void getFileDirectory() {
        String directory = getDirectory();
        assertEquals("/home/romantic-coder/temp", directory);
    }
    
    public String getDirectory() {
        return tempFile.getDirectory();
    }
}
