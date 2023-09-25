package com.mohammadDarwish.codefellowship.controllers;

import com.mohammadDarwish.codefellowship.models.ApplicationUser;
import com.mohammadDarwish.codefellowship.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup.html";
    }

    @GetMapping("/logout")
    public String getLogoutPage(){
        return "index.html";
    }

    @GetMapping("/")
    public String getHomePage(Principal principal, Model model){
        if(principal!=null){
            String username=principal.getName();
            ApplicationUser applicationUser=applicationUserRepository.findByUsername(username);
            model.addAttribute("applicationUser",applicationUser);
        }
        return "/index.html";
    }

    @PostMapping("/signup")
    public RedirectView creatUser(String username,String password,String firstName,String lastName,String dateOfBirth,String bio){
        ApplicationUser applicationUser=new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setPassword(passwordEncoder.encode(password));
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setDateOfBirth(dateOfBirth);
        applicationUser.setBio(bio);
        applicationUserRepository.save(applicationUser);
        authWithHttpServletRequest(applicationUser.getUsername(),applicationUser.getPassword());
        return new RedirectView("/");
    }



    public void authWithHttpServletRequest(String username,String password){
        try {
            request.login(username,password);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }











}
