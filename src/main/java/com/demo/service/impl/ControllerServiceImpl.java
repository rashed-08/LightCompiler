package com.demo.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Solution;
import com.demo.service.ControllerService;

@Service
public class ControllerServiceImpl implements ControllerService {
    
    @Autowired
    private CodeCompileImpl codeCompile;

    @Autowired
    private CodeExecuteImpl codeExecute;

    @Override
    public int compile(Solution solution) {
        return codeCompile.compileCode(solution);
    }

    @Override 
    public ArrayList<String> execute() {
        ArrayList<String> outputList = new ArrayList<>();
        outputList = codeCompile.executeCode();
        if (!outputList.isEmpty()) {
            System.out.println(outputList);
        } else {
            System.out.println("Time Limit Exeed!");
        }
        return outputList;
    }

    @Override
    public int judge(Solution solution) {
        ArrayList<String> outputList = new ArrayList<>();
        String executableFile = codeCompile.getExecutableFile();
        String language = codeCompile.getLanguage(solution);
        String stdIn = codeCompile.getSTDIN(solution);
        outputList = codeExecute.codeExecute(executableFile, language, stdIn);

//        outputList = codeCompile.executeCode();
        if (!outputList.isEmpty()) {
            System.out.println(outputList);
        } else {
            System.out.println("Time Limit Exeed!");
        }
        return 0;
    }

}
