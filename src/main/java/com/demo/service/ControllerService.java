package com.demo.service;

import com.demo.model.Solution;

public interface ControllerService {
    
    public int compile(Solution solution);
    public void execute();
}
