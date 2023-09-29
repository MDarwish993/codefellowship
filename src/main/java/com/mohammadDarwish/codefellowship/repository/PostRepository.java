package com.mohammadDarwish.codefellowship.repository;

import com.mohammadDarwish.codefellowship.models.ApplicationUser;
import com.mohammadDarwish.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByApplicationUserIn(Set<ApplicationUser> followed);
}
