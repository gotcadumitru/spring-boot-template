package com.dima.demo.user;

import com.dima.demo.security.config.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping(path = "all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "me")
    public User getAuthUser(@RequestHeader(name="Authorization") String authorizationHeader){
        return userService.getUserByJwtToken(jwtUtils.getFromHeader(authorizationHeader));
    }
}
