package com.example.bootstrap;

import com.example.model.Post;
import com.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by D020447 on 01.06.2016.
 */
@Component
public class PostLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PostRepository repository;

    @Autowired
    public void setPostRepository(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (int i = 0; i < 5; i++) {
            repository.save(new Post("My post number #" + (i + 1)));
        }
    }
}
