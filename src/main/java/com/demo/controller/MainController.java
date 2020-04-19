package com.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.model.Solution;
import com.demo.service.impl.ControllerServiceImpl;

@Controller
public class MainController {
    
    @Autowired
    private ControllerServiceImpl controllerService;

    @RequestMapping(value = {"/","/solution"})
    public String showSubmitPage(Model model) {
        model.addAttribute("solution", new Solution());
        return "solution";
    }
    
    @RequestMapping(value = "/solution", method = RequestMethod.POST)
    public String compile(@ModelAttribute final Solution solution, final BindingResult result){
        ArrayList<String> outputList;
        if (result.hasErrors()) {
            return "solution";
        }
        int successfullyExited = controllerService.compile(solution);
        if (successfullyExited == 0) {
            outputList = controllerService.execute();
        }
        return "solution";
    }
}
