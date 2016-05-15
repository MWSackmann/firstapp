package com.example.repository;

import com.example.model.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sackmann on 02.05.2016.
 */
public interface PostRepository extends CrudRepository<Post, Long>{
}
