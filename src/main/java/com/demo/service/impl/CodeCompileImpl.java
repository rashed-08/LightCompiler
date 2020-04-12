package com.demo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.CodeCompile;

@Service
public class CodeCompileImpl implements CodeCompile {

    String[] command = new String[4];
    
    private String executableFile = "";     
    private String sourceCodeFile = "";
    
    private String fileName = "";
    private String STDIN = "";
    private String errorTag = ""; 
    
    @Autowired
    private TempFileImpl tempFile;

    @Autowired
    private WriteToFileImpl writeFile;

    @Override
    public void createFile(String directory,String filename) {
        tempFile.createFile(directory, filename);
    }

    @Override
    public void writeSourceCode(String sourceCode) {
        String directory = getDirectory();
        writeFile.writeSourceCode(directory, fileName, sourceCode);
    }

    @Override
    public int compileCode(Solution solution) {
        
        String directory = getDirectory();
        
        @SuppressWarnings("unused")
        String compilationResult = "";
        int exitValue = 1;
        
        String sourceCode = solution.getSolutionSourceCode();
        if (solution.getLanguage().equals("c")) {
            prepare("c",sourceCode,directory);
        } else if (solution.getLanguage().equals("cpp")) {
            prepare("cpp",sourceCode,directory);
        } else if (solution.getLanguage().equals("java")) {
            prepare("java",sourceCode,directory);
        }
        
        try {
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
            
            if (exitValue == 1) {
                errorTag += "error";
                deleteFile(errorTag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exitValue;
    }

    @Override
    public ArrayList<String> executeCode() {
        
        ArrayList<String> outputList = new ArrayList<>();       
        String line = "";
        String[] command = { executableFile };

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);

        try {
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStramReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStramReader);
            
            try {
                OutputStream stdin = process.getOutputStream();
                stdin.write(STDIN.getBytes());
                stdin.flush();
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while ((line = bufferedReader.readLine()) != null) {
                    outputList.add(line);
                }
                process.destroy();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
        deleteFile(errorTag);
        return outputList;
    }

    @Override
    public void deleteFile(String errorTag) {
        String directory = getDirectory();
        tempFile.deleteFile(directory, fileName, errorTag);
    }

    @Override
    public String getSTDIN(Solution solution) {
        STDIN = solution.getStdin();
        STDIN += "\n";
        return STDIN;
    }
    
    @Override
    public String getDirectory() {
        String directory = tempFile.getDirectory();
        return directory;
    }

    @Override
    public void prepare(String languageName, String sourceCode, String directory) {
        if (languageName.equals("c")) {            
            fileName = "filename." + languageName;
            createFile(directory, fileName);
            writeSourceCode(sourceCode);
            executableFile = directory + "filename"; 
            sourceCodeFile = directory + fileName;
            command[0] = "gcc";
            command[1] = sourceCodeFile;
            command[2] = "-o";
            command[3] = executableFile;
        } else if (languageName.equals("cpp")) {
            fileName = "filename." + languageName;
            createFile(directory, fileName);
            writeSourceCode(sourceCode);
            executableFile = directory + "filename"; 
            sourceCodeFile = directory + fileName;
            command[0] = "g++";
            command[1] = sourceCodeFile;
            command[2] = "-o";
            command[3] = executableFile;            
        } else if (languageName.equals("java")) {
            fileName = "filename." + languageName;
            createFile(directory, fileName);
            writeSourceCode(sourceCode);
            executableFile = directory + "filename"; 
            sourceCodeFile = directory + fileName;
            command[0] = "javac";
            command[1] = sourceCodeFile;
            command[2] = "-o";
            command[3] = executableFile;            
        }
    }

}
