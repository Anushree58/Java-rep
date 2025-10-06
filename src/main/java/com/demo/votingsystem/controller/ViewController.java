package com.demo.votingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showHome() {
        return "index"; // points to src/main/resources/templates/index.html
    }

    @GetMapping("/dashboard")
    public String showUserDashboard() {
        return "dashboard";
    }

    @GetMapping("/admin")
    public String showAdminDashboard() {
        return "admin";
    }
}
