package com.portfolio.backend.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class skillController {
    public String getSkills(Model model){
        model.addAttribute("skills", "Skills");
        return "Skills";
    }
}
