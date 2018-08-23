package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.dataModel.Bill;
import com.whereIsMyMoney.dataModel.Purchase;
import com.whereIsMyMoney.dataModel.PurchasesWrapper;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.BillService;
import com.whereIsMyMoney.service.PurchaseService;
import com.whereIsMyMoney.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseService purchaseService;

    public BillController() {
    }

    @GetMapping("/bills")
    @ResponseStatus(HttpStatus.OK)
    public List<Bill> getAllBills() {
        List<Bill> bills = billService.getAll();
        if( bills == null ) {
            throw new DataNotFoundException("Bills list not found");
        }
        return bills;
    }

//    @GetMapping("/users/{userId}/bills")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Bill> getUserBills(@PathVariable int userId ) {
//        userExist(userId);
//        List<Bill> bills = billService.getByUserId(userId);
//        if(bills == null || bills.isEmpty()) {
//            throw new DataNotFoundException("Bills list not found");
//        }
//        return bills;
//    }

    @GetMapping(value = "/bills/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Bill getBill(@PathVariable int id ) {
        billExist(id);
        return billService.getOne(id);
    }

    @RequestMapping( value = "/bills", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Bill add(@RequestBody Bill theBill) {
        if(billService.exists(theBill.getId())) {
            throw new DataExistsException("Bill with id '" + theBill.getId() + "' already exists");
        }
        return billService.add(theBill);
    }

    @RequestMapping( value = "/bills/purchases", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<String> addPurchasesToBill(@RequestBody PurchasesWrapper wrapper){
        List<String> response = new ArrayList<>();
        for(Purchase purchase: wrapper.getPurchases() ){
            purchaseService.add(purchase);
            response.add(purchase.toString());
        }
        return response;
    }

    @PutMapping("/bills/{id}" )
    @ResponseStatus(HttpStatus.OK)
    public Bill updateBill(@RequestBody Bill theBill) {
        billExist(theBill.getId());
        return billService.update(theBill);
    }

    @DeleteMapping("/bills/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBill(@PathVariable("id") int id) {
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
