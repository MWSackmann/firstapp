package com.example.firstapp.repository;

import com.example.firstapp.model.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sackmann on 02.05.2016.
 */
public interface PostRepository extends CrudRepository<Post, Long>{
}
