package com.example.repository;

import com.example.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sackmann on 02.05.2016.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    // default sort by primary key
    List<Post> findAllByOrderByIdAsc();

    // newest post on top
    List<Post> findAllByOrderByCreatedAtDesc();
}

//public interface PostRepository extends RevisionRepository<Post, Long, Integer>, CrudRepository<Post, Long> {
//    // default sort by primary key
//    List<Post> findAllByOrderByIdAsc();
//    // newest post on top
//    List<Post> findAllByOrderByCreatedAtDesc();
//}