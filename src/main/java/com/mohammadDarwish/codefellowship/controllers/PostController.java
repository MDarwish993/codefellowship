package com.mohammadDarwish.codefellowship.controllers;

import com.mohammadDarwish.codefellowship.models.ApplicationUser;
import com.mohammadDarwish.codefellowship.models.Post;
import com.mohammadDarwish.codefellowship.repository.ApplicationUserRepository;
import com.mohammadDarwish.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    private HttpServletRequest request;


    @PostMapping("/addPost")
    public RedirectView addPost(Principal principal, String body) {
        if (principal != null) {
            String username = principal.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            if (applicationUser != null) {
                Post post = new Post(body, applicationUser);
                post.setCreatedAt(LocalDate.now());
                postRepository.save(post);
                return new RedirectView("/myprofile");
            }
        }
        return new RedirectView("/myprofile");
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            if (applicationUser != null) {
                model.addAttribute("applicationUser", applicationUser);
                model.addAttribute("postList", applicationUser.getPostList());
            }
        }

        return "myprofile.html";
    }


    @GetMapping("/users/{id}")
    public String getUsers(Principal principal, Model model,@PathVariable Long id) {
        if (principal != null) {
            String username = principal.getName();
            ApplicationUser user = applicationUserRepository.findByUsername(username);
            model.addAttribute("user", user);
        }
        ApplicationUser applicationUser=applicationUserRepository.findById(id).orElseThrow();
        model.addAttribute("applicationUser", applicationUser);
        model.addAttribute("post", applicationUser.getPostList());



        return "users.html";
    }



}
