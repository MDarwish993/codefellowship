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

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class FollowController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;
    @PostMapping("/follow/{userId}")
    public RedirectView followUser(@PathVariable Long userId, Principal principal){
        ApplicationUser currentApplicationUser=applicationUserRepository.findByUsername(principal.getName());
        ApplicationUser wantFollowApplicationUser=applicationUserRepository.findById(userId).orElseThrow();
        if(currentApplicationUser!=null&&wantFollowApplicationUser!=null){
            wantFollowApplicationUser.getFollowers().add(currentApplicationUser);
            applicationUserRepository.save(wantFollowApplicationUser);
        }
        return new RedirectView("/users/"+userId);

    }

    @GetMapping("/feed")
    public String getAllFeed(Principal p , Model m){
        if (p != null)
        {
            ApplicationUser myUser = applicationUserRepository.findByUsername(p.getName());
            Set<ApplicationUser> followed = myUser.getFollowing();
            followed.remove(myUser);
            List<Post> posts = postRepository.findAllByApplicationUserIn(followed);
            m.addAttribute("posts", posts);
        }
        return "feed";
    }
}
