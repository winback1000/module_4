package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping({"","/greeting"})
    public String greeting(Model model) {
        model.addAttribute("greeting", "Welcome to the first Spring project");
        return "index";
    }
}
