package com.demo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.CodeCompile;

@Service
public class CodeCompileImpl implements CodeCompile {

    private String[] command = new String[4];
    private String[] javaCompiler = new String[2];
    
    private String executableFile = "";
    private String sourceCodeFile = "";
    
    private String language = "";
    
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
        
        String sourceCode = getSourceCode(solution);
        language = getLanguage(solution);
        
        if (language.equals("c")) {
            prepare("c",sourceCode,directory);
        } else if (language.equals("cpp")) {
            prepare("cpp",sourceCode,directory);
        } else if (language.equals("java")) {
            prepare("java",sourceCode,directory);
            javaCompiler[0] = "javac";
            javaCompiler[1] = sourceCodeFile;
        }
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (language.equals("java")) {
                processBuilder.command(javaCompiler);
            } else {
                processBuilder.command(command);
            }
            
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
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (language.equals("java")) {
            String[] command = new String[4];
            command[0] = "java";
            command[1] = "-cp";
            command[2] = getDirectory();
            command[3] = executableFile;
            processBuilder.command(command);
        } else {
            String[] command = { executableFile };
            processBuilder.command(command);
        }
        
        try {
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStramReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStramReader);
            
            boolean timeout = true;
            try {
                OutputStream stdin = process.getOutputStream();
                stdin.write(STDIN.getBytes());
                stdin.flush();
                timeout = process.waitFor(6, TimeUnit.SECONDS);
                if (!timeout) {
                    long start = System.currentTimeMillis();
                    Thread.sleep(6001);
                    long end = System.currentTimeMillis();
                    if ((end-start)>=6000) {
                        Thread.interrupted();
                        process.destroy();
                        process.destroyForcibly();
                    }
                }
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
    public String getLanguage(Solution solution) {
        String language = solution.getLanguage();
        return language;
    }

    @Override
    public String getSourceCode(Solution solution) {
        String solutionSourceCode = solution.getSolutionSourceCode();
        return solutionSourceCode;
    }

    @Override
    public String getDirectory() {
        String directory = tempFile.getDirectory();
        return directory;
    }

    public String getExecutableFile() {
        return executableFile;
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
            fileName = "Main." + languageName;
            createFile(directory, fileName);
            writeSourceCode(sourceCode);
            executableFile = "Main"; 
            sourceCodeFile = directory + fileName;
        }
    }

}

