package ru.progmatik.sarafan.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.progmatik.sarafan.domain.User;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){
        final HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", user);
        data.put("messages", null);
        model.addAttribute("frondendData", data);
        return "main";
    }
}