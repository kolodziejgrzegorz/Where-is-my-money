package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Purchase;
import com.whereIsMyMoney.domain.PurchasesList;
import com.whereIsMyMoney.service.BillService;
import com.whereIsMyMoney.service.PurchaseService;
import com.whereIsMyMoney.service.UserService;
import org.springframework.http.HttpStatus;
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
    public List<Bill> getAllBills() {
         return billService.getAll();
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

    @GetMapping(value = "/bills/{id}")
    public Bill getBill(@PathVariable int id ) {
        return billService.getOne(id);
    }

    @PostMapping( value = "/bills")
    @ResponseStatus(HttpStatus.CREATED)
    public Bill addNew(@RequestBody Bill theBill) {
        return billService.addNew(theBill);
    }

    @PostMapping( value = "/bills/purchases")
    public List<String> addPurchasesToBill(@RequestBody PurchasesList purchasesList){
        List<String> response = new ArrayList<>();
        for(Purchase purchase: purchasesList.getPurchases() ){
            purchaseService.add(purchase);
            response.add(purchase.toString());
        }
        return response;
    }

    @PutMapping("/bills/{id}" )
    public Bill updateBill(@RequestBody Bill theBill, @PathVariable int id) {
        return billService.update(id,theBill);
    }

    @DeleteMapping("/bills/{id}")
    public void deleteBill(@PathVariable("id") int id) {
        billService.delete(id);
    }
}
