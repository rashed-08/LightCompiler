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

    @Override
    public int compile(Solution solution) {
        int successfullyExit = codeCompile.compileCode(solution);
        return successfullyExit;
    }

    @Override 
    public ArrayList<String> execute() {
        ArrayList<String> outputList = new ArrayList<>();
        outputList = codeCompile.executeCode();
        return outputList;
    }

}
