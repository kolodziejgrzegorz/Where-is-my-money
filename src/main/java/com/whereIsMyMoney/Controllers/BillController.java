package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.model.Bill;
import com.whereIsMyMoney.model.Purchase;
import com.whereIsMyMoney.model.PurchasesWrapper;
import com.whereIsMyMoney.service.BillService;
import com.whereIsMyMoney.service.PurchaseService;
import com.whereIsMyMoney.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BillController {

    private final BillService billService;
    private final UserService userService;
    private final PurchaseService purchaseService;

    public BillController(BillService billService, UserService userService, PurchaseService purchaseService) {
        this.billService = billService;
        this.userService = userService;
        this.purchaseService = purchaseService;
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
        return billService.save(theBill);
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
    public Bill updateBill(@RequestBody Bill theBill, @PathVariable int id) {
        billExist(id);
        theBill.setId(id);
        System.out.println(theBill);
        Bill updatedBill = billService.update(theBill);
        System.out.println(updatedBill);
        return updatedBill;
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
