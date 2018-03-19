package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.dataModel.Bill;
import com.whereIsMyMoney.dataModel.User;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.BillService;
import com.whereIsMyMoney.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    public BillController() {
    }

//    @GetMapping("/bills")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Bill> getAllBills() {
//        List<Bill> bills = billService.getAll();
//        if(bills == null || bills.isEmpty()) {
//            throw new DataNotFoundException("Bills list not found");
//        }
//        return bills;
//    }

    @GetMapping("/users/{userId}/bills")
    @ResponseStatus(HttpStatus.OK)
    public List<Bill> getUserBills(@PathVariable int userId ) {
        userExist(userId);
        List<Bill> bills = billService.getByUserId(userId);
        if(bills == null || bills.isEmpty()) {
            throw new DataNotFoundException("Bills list not found");
        }
        return bills;
    }

    @GetMapping(value = "/users/{userId}/bills/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Bill getBill(@PathVariable int userId, @PathVariable int id ) {
        userExist(userId);
        billExist(id);
        return billService.getOne(id);
    }

    @PostMapping(value = "/users/{userId}/bills",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable int userId, @RequestBody Bill theBill) {
        userExist(userId);
        if(billService.exists(theBill.getId())) {
            throw new DataExistsException("Bill with id '" + theBill.getId() + "' already exists");
        }
        User user = userService.getOne(userId);
        theBill.setUser(user);
        billService.add(theBill);
    }

    @PutMapping("/users/{userId}/bills/{id}" )
    @ResponseStatus(HttpStatus.OK)
    public void updateBill(@PathVariable int userId, @RequestBody Bill theBill) {
        userExist(userId);
        billExist(theBill.getId());
        billService.update(theBill);
    }

    @DeleteMapping("/users/{userId}/bills/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBill(@PathVariable int userId, @PathVariable("id") int id) {
        userExist(userId);
        billExist(id);
        billService.delete(id);
    }

    private void userExist(int id){
        if(!userService.exists(id)){
            throw new DataNotFoundException("User with id: " + id + " not found");
        }
    }
    private void billExist(int id){
        if(!billService.exists(id)) {
            throw new  DataNotFoundException("Bill with Id = " + id + " not found ");
        }
    }

}
