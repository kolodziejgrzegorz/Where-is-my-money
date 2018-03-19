package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.dataModel.Bill;
import com.whereIsMyMoney.dataModel.Purchase;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BillController billController;

    public PurchaseController() {

    }

//    @GetMapping("/purchases")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Purchase> getAllPurchases() {
//        List<Purchase> purchases = purchaseService.getAll();
//        if(purchases==null) {
//            throw new DataNotFoundException("Purchases list not found");
//        }
//        return purchases;
//    }

    @GetMapping("users/{userId}/bills/{billId}/purchases")
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> getBillPurchases(@PathVariable int userId, @PathVariable int billId) {
        Bill bill = billController.getBill(userId,  billId);
        List<Purchase> purchases = purchaseService.getByBillId(billId);
        if(purchases==null) {
            throw new DataNotFoundException("Purchases list not found");
        }
        return purchases;
    }

    @GetMapping(value = "users/{userId}/bills/{billId}/purchases/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Purchase getPurchase(@PathVariable int userId, @PathVariable int billId, @PathVariable("id") int id) {
        purchaseExist(id);
        Bill bill = billController.getBill(userId,  billId);
        return purchaseService.getOne(id);
    }

    @PostMapping("users/{userId}/bills/{billId}/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable int userId, @PathVariable int billId, @RequestBody Purchase thePurchase) {
        if(purchaseService.exists(thePurchase.getId())) {
            throw new DataExistsException("Purchase with id '" + thePurchase.getId() + "' already exists");
        }
        Bill bill = billController.getBill(userId,  billId);
        thePurchase.setBill(bill);
        purchaseService.add(thePurchase);
    }

    @PutMapping("users/{userId}/bills/{billId}/purchases/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePurchase(@PathVariable int userId, @PathVariable int billId, @RequestBody Purchase thePurchase) {
        purchaseExist(thePurchase.getId());
        Bill bill = billController.getBill(userId,  billId);
        purchaseService.update(thePurchase);
    }

    @DeleteMapping("users/{userId}/bills/{billId}/purchases/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePurchase(@PathVariable int userId, @PathVariable int billId, @PathVariable("id") int id) {
        purchaseExist(id);
        Bill bill = billController.getBill(userId,  billId);
        purchaseService.delete(id);
    }

    private void purchaseExist(int id){
        if(!purchaseService.exists(id)) {
            throw new  DataNotFoundException("Purchase with Id = " + id + " not found ");
        }
    }
}
