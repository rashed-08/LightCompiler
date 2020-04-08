package com.demo.service;

import java.util.ArrayList;

import com.demo.model.Solution;

public interface ControllerService {
    
    public int compile(Solution solution);
    public ArrayList<String> execute();
}
