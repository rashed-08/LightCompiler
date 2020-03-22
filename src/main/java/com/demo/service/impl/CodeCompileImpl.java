package com.demo.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.CodeCompile;

@Service
public class CodeCompileImpl implements CodeCompile {

    @Autowired
    private TempFileImpl tempFile;

    @Override
    public void createFile(String filename) {
        tempFile.createFile(filename);
    }

    @Override
    public String compileCode(Solution solution) {
        String fileName = "";
        if (solution.getLanguage().equals("c")) {
            fileName = "filename.c";
            createFile(fileName);
        }

        try {
            @SuppressWarnings("unused")
            String compilationResult = "";

            String code = "/home/romantic-coder/Documents/workspace-spring-tool-suite-4-4.5.1.RELEASE/demo/temp/filename.c";
            String exec = "/home/romantic-coder/Documents/workspace-spring-tool-suite-4-4.5.1.RELEASE/demo/temp/filename";

            String[] command = { "gcc", code, "-o", exec };

            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.command(command);

            Process compileProcess = processBuilder.start();

            BufferedReader compileError = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String showError = compileError.readLine();
            if (showError != null) {
                System.out.println("Compilation Error: " + showError);
            }
            compilationResult += showError + "\n";

            BufferedReader compileRun = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String outputCompile = compileRun.readLine();
            if (compileRun != null) {
                System.out.println("Compiler Output: " + compileRun);
            }
            compilationResult += outputCompile + "\n";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void deleteFile() {
        tempFile.deleteFile();
    }

}
