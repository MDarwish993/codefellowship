package com.mohammadDarwish.codefellowship.repository;

import com.mohammadDarwish.codefellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
}
