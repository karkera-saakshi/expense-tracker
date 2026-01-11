package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.LoginDto;
import com.example.expense_tracker.dto.LoginResponse;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @PostMapping("/signup")
    public String signup(@RequestBody User user)
    {
        userRepo.save(user);
        return "You are successfully registered!";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto u)
    {
        User user = userRepo.findByUsername(u.getUsername());
        if(user == null)
        {
            return "User not found";
        }
        if (!user.getPassword().equals(u.getPassword()))
        {
            return "You entered the wrong password";
        }
        return String.valueOf(user.getId());
    }

    
}
