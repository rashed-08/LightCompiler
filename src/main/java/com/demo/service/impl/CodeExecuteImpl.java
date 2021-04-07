package com.demo.service.impl;

import com.demo.service.CodeCompile;
import com.demo.service.CodeExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecuteImpl implements CodeExecute {

    @Autowired
    private CodeCompile codeCompile;

    @Override
    public ArrayList<String> codeExecute(String executableFile, String language, String STDIN) {
                ArrayList<String> outputList = new ArrayList<>();
        String line = "";
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (language.equals("java")) {
            String[] command = new String[4];
            command[0] = "java";
            command[1] = "-cp";
            command[2] = codeCompile.getDirectory();
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
        codeCompile.deleteFile("");
        return outputList;
    }
}
