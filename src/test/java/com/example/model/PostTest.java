package com.example.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sackmann on 17.05.2016.
 */
public class PostTest {

    private Post post;
    @Before
    public void setUp() throws Exception {
        post = new Post();
    }

    @Test
    public void getId() throws Exception {
        post.setId(99);
        assertEquals(99, post.getId());
    }

    @Test
    public void getMessage() throws Exception {
        post.setMessage("hugo");
        assertEquals("hugo", post.getMessage());
    }

    @Test
    public void getCreatedAt() throws Exception {
        // createdAt must be created in constructor
        assertNotEquals(null, post.getCreatedAt());
    }
}