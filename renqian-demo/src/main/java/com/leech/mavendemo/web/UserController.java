package com.leech.mavendemo.web;

import com.leech.mavendemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {


    @PostMapping("/add")
    public String add(@RequestBody User user){
        log.info("user={}", user);
        return "success";
    }


}
