package org.example.pharma.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

import org.example.pharma.model.User;
import org.example.pharma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    public AuthController() {
    }

    @GetMapping({"/register"})
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping({"/register"})
    public String register(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (this.userService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email déjà utilisé.");
            return "redirect:/register";
        } else {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            this.userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Inscription réussie.");
            return "redirect:/login";
        }
    }

    @GetMapping({"/login"})
    public String showLoginForm() {
        return "login";
    }

    @PostMapping({"/login"})
    public String login(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> user = this.userService.authenticate(email, password);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            return "redirect:/index.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "Identifiants invalides.");
            return "redirect:/login";
        }
    }

    @GetMapping({"/dashboard"})
    public String showDashboard(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("user", user);
            return "dashboard";
        }
    }

    @GetMapping({"/logout"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }
}
