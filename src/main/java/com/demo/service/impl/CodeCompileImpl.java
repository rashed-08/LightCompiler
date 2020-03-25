package com.demo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.CodeCompile;

@Service
public class CodeCompileImpl implements CodeCompile {

    private String code = "/home/romantic-coder/Documents/workspace-spring-tool-suite-4-4.5.1.RELEASE/demo/temp/filename.c";
    private String executableFile = "/home/romantic-coder/Documents/workspace-spring-tool-suite-4-4.5.1.RELEASE/demo/temp/filename";
    
    private String STDIN = "";
    private String errorTag = "";
    
    @Autowired
    private TempFileImpl tempFile;

    @Autowired
    private WriteToFileImpl writeFile;

    @Override
    public void createFile(String filename) {
        tempFile.createFile(filename);
    }

    @Override
    public void writeSourceCode(String sourceCode) {
        writeFile.writeSourceCode(sourceCode);
    }

    @Override
    public int compileCode(Solution solution) {
        String fileName = "";
        @SuppressWarnings("unused")
        String compilationResult = "";
        int exitValue = 1;
        
        if (solution.getLanguage().equals("c")) {
            fileName = "filename.c";
            createFile(fileName);
            writeSourceCode(solution.getSolutionSourceCode());
        }

        try {

            String[] command = { "gcc", code, "-o", executableFile };

            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.command(command);

            Process compileProcess = processBuilder.start();

            BufferedReader compileError = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String showError = compileError.readLine();
            if (showError != null) {
                System.out.println("Compilation Error: " + showError);
            }
            compilationResult += showError + "\n";
            
            STDIN = getSTDIN(solution);
            
            exitValue = compileProcess.waitFor();
            System.out.println("Exit value: " + exitValue);
            
            if (exitValue == 1) {
                errorTag += "error";
                tempFile.deleteFile(errorTag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exitValue;
    }

    @Override
    public String executeCode() {
        
        String line = "";

        String[] command = { executableFile };

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);

        try {
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();

            InputStreamReader inputStramReader = new InputStreamReader(inputStream);
 
            BufferedReader bufferedReader = new BufferedReader(inputStramReader);
            
            OutputStream stdin = process.getOutputStream();
            stdin.write(STDIN.getBytes());
            stdin.flush();
            try {
                int exitValue = process.waitFor();
                System.out.println("Exit value: " + exitValue);
              //  process.destroy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteFile();
        return line;
    }

    @Override
    public void deleteFile() {
        tempFile.deleteFile(errorTag);
    }

    @Override
    public String getSTDIN(Solution solution) {
        STDIN = solution.getStdin();
        STDIN += "\n";
        return STDIN;
    }

}
