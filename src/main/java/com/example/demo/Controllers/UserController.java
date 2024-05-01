package com.example.demo.Controllers;

import com.example.demo.Entities.UserEntity;
import com.example.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update/user")
    public ResponseEntity<Object> updateUser(@RequestBody UserEntity userEntity){
        return userService.updateUser(userEntity);
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Object> delete(@PathVariable String email){

        return userService.delete(email);
    }
}
