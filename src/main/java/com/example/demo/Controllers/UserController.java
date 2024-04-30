package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RequiredArgsConstructor
@Controller
public class UserController {
private final UserService userService;
    @GetMapping("/hey/{x}")
    public ResponseEntity<Object> firstApi(@PathVariable String x){

        return new ResponseEntity<>("Hello "+x, HttpStatus.OK);
    }
    @PostMapping("/create/user")
    public ResponseEntity<Object> createUser(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id){

        return userService.findById(id);
    }
}
