package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.dataModel.User;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        List<User> users = userService.getAll();
        if(users == null || users.isEmpty()) {
            throw new DataNotFoundException("Users list not found");
        }
        return users;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("id") int id) {
        userExist(id);
        return userService.getOne(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody User theUser) {
        if(userService.exists(theUser.getId())) {
            throw new  DataNotFoundException("User with Id = " + theUser.getId() + " already exists ");
        }
        userService.add(theUser);
    }

    @PutMapping( "/users/{id}" )
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody User theUser) {
        userExist(theUser.getId());
        userService.update(theUser);
    }

    @DeleteMapping( "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") int id) {
        userExist(id);
        userService.delete(id);
    }

    private void userExist(int id){
        if(!userService.exists(id)) {
            throw new  DataNotFoundException("User with Id = " + id + " not found ");
        }
    }
}
