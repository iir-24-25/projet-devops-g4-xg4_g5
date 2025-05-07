//package org.example.pharma.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class WelcomeController {
//
//    @GetMapping("/welcome")
//    public String welcome(Authentication authentication, Model model) {
//        // Ajouter le nom d'utilisateur au modèle
//        model.addAttribute("username", authentication.getName());
//        return "welcome"; // Nom du template (welcome.html)
//    }
//}