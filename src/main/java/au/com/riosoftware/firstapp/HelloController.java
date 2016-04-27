package au.com.riosoftware.firstapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

//  this maps method to request url and states that the response has no certain type (like json)
    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello";
    }
}
