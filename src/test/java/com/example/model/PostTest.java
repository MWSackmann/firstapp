package com.example.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

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

        Date date = new Date();
        post.setCreatedAt(date);
        assertEquals(date, post.getCreatedAt());
    }
    @Test
    public void testConstructor() throws Exception{

        post = new Post("New Post");

        assertEquals("New Post", post.getMessage());
        // createdAt must be created already within constructor
        assertNotEquals(null, post.getCreatedAt());
        // id is auto-generated
        assertNotEquals(null, post.getId());

    }
}