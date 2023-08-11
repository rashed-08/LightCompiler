package com.demo.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.demo.service.prepare.CPPPrepareExecutable;
import com.demo.service.prepare.CPrepareExecutable;
import com.demo.service.prepare.JavaPrepareExecutable;
import com.demo.service.prepare.PrepareExecutableFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.CodeCompile;

@Service
public class CodeCompileImpl implements CodeCompile {

    private PrepareExecutableFile prepareExecutableFile;
    private String executableFile = "";
    
    private String language = "";
    
    private String fileName = "";
    private String STDIN = "";
    private String errorTag = ""; 
    
    @Autowired
    private TempFileImpl tempFile;

    @Override
    public int compileCode(Solution solution) {

        String[] command = new String[4];
        String[] javaCompiler = new String[2];

        String directory = getDirectory();
        
        @SuppressWarnings("unused")
        String compilationResult = "";
        int exitValue = 1;
        
        String sourceCode = solution.getSolutionSourceCode();
        language = solution.getLanguage();
        
        if (language.equals("c")) {
            prepareExecutableFile = new PrepareExecutableFile();
            prepareExecutableFile.setPrepareExecutable(new CPrepareExecutable());
            command = prepareExecutableFile.prepare(sourceCode, directory);
            executableFile = command[3];
        } else if (language.equals("cpp")) {
            prepareExecutableFile = new PrepareExecutableFile();
            prepareExecutableFile.setPrepareExecutable(new CPPPrepareExecutable());
            command = prepareExecutableFile.prepare(sourceCode, directory);
            executableFile = command[3];
        } else if (language.equals("java")) {
            prepareExecutableFile = new PrepareExecutableFile();
            prepareExecutableFile.setPrepareExecutable(new JavaPrepareExecutable());
            command = prepareExecutableFile.prepare(sourceCode, directory);
            javaCompiler[0] = "javac";
            javaCompiler[1] = command[1];
            executableFile = command[3];
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
                deleteFiles(errorTag);
            }
            String removeSourceFile = command[1];
            tempFile.deleteFile(removeSourceFile);
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
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

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

        if (language.equalsIgnoreCase("java")) {
            String removeExecutableFile = getDirectory() + File.separator + executableFile + ".class";
            tempFile.deleteFile(removeExecutableFile);
        } else {
            tempFile.deleteFile(executableFile);
        }
        
        return outputList;
    }

    @Override
    public void deleteFiles(String errorTag) {
        String directory = getDirectory();
        tempFile.deleteFiles(directory, fileName, errorTag);
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
}

