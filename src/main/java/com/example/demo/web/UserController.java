package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> addUserToBoard(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error: result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
        }
        User newUser = userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {return userService.getAll();}

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable Long user_id) {
        User user = userService.getUserById(user_id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }
}
