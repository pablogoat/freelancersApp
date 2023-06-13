package com.FreelancersBackend.dao;

import com.FreelancersBackend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Post, Integer> {

    List<Post> findByTitle(String title);
    Optional<Post> findById(Integer id);
}
