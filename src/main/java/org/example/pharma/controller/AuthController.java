package org.example.pharma.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.pharma.entity.User;
import org.example.pharma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String redirectToRegisterPage() {
        return "redirect:/register.html";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (userService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email déjà utilisé.");
            return "redirect:/register.html";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.registerUser(user);

        redirectAttributes.addFlashAttribute("success", "Inscription réussie.");
        return "redirect:/login.html";
    }

    @GetMapping("/login")
    public String redirectToLoginPage() {
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            return "redirect:/index.html";
        } else {
            redirectAttributes.addFlashAttribute("error", "Identifiants invalides.");
            return "redirect:/login.html";
        }
    }

    @GetMapping("/index")
    public String redirectToDashboard(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/index.html";
        }

        return "redirect:/index.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }
}
