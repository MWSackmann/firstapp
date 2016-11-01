package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by d020447 on 17.05.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @Test
    public void contextLoads() {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

}