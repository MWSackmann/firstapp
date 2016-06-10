package com.example.repository;

import com.example.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sackmann on 02.05.2016.
 */
public interface PostRepository extends CrudRepository<Post, Long> {
    // default sort by primary key
    List<Post> findAllByOrderByIdAsc();

    // newest post on top
    List<Post> findAllByOrderByCreatedAtDesc();
}
