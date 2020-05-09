package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Solution;
import com.demo.service.impl.ControllerServiceImpl;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MainController {
     
    @Autowired
    private ControllerServiceImpl controllerService;
    
    @PostMapping("/submit")
    public ResponseEntity<List<String>> compile(@RequestBody final Solution solution, final BindingResult result){
        List<String> outputList = new ArrayList<>();
        int successfullyExited = controllerService.compile(solution);
        if (successfullyExited == 0) {
            outputList = controllerService.execute();
        }
        return ResponseEntity.ok().body(outputList);
    }
}
