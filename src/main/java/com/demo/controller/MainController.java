package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.model.Solution;
import com.demo.service.impl.ControllerServiceImpl;

@Controller
public class MainController {
    
    @Autowired
    private ControllerServiceImpl controllerService;
    
    @RequestMapping("/")
    public String showHome(final Model model) {
        model.addAttribute("title", "Spring Boot with Docker");
        model.addAttribute("message", "Welcome to Docker container!");
        return "index";
    }

    @RequestMapping(value = "/solution")
    public String showSubmitPage(Model model) {
        model.addAttribute("solution", new Solution());
        return "solution";
    }
    
    @RequestMapping(value = "/solution", method = RequestMethod.POST)
    public String compile(final Solution solution){
        controllerService.compile(solution);
        return "compile";
    }
}
