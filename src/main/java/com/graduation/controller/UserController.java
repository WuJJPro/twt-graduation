package com.graduation.controller;

import com.graduation.component.JsonResult;
import com.graduation.entity.User;
import com.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("api/user/get")
    public JsonResult<User> getUser(@RequestHeader("token") String userid){
        User user = userService.getUser(userid);
        return new JsonResult<>(user);
    }
}
