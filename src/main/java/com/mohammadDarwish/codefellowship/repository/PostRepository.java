package com.mohammadDarwish.codefellowship.repository;

import com.mohammadDarwish.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
