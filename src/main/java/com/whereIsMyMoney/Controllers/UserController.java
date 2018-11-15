package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.domain.User;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        return userService.findById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody User theUser) {
        if(userService.exists(theUser.getId())) {
            throw new  DataNotFoundException("User with Id = " + theUser.getId() + " already exists ");
        }
        return userService.addNew(theUser);
    }

    @PutMapping( "/users/{id}" )
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User theUser) {
        userExist(theUser.getId());
        return userService.update(theUser);
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
