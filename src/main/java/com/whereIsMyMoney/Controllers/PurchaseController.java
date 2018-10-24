package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.model.Bill;
import com.whereIsMyMoney.model.Purchase;
import com.whereIsMyMoney.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BillController billController;

    public PurchaseController() {

    }

    @GetMapping("/purchases")
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAll();
        if(purchases==null) {
            throw new DataNotFoundException("Purchases list not found");
        }
        return purchases;
    }

    @GetMapping("/bills/{billId}/purchases")
    @ResponseStatus(HttpStatus.OK)
    public List<Purchase> getBillPurchases(@PathVariable int billId) {
        Bill bill = billController.getBill(billId);
        List<Purchase> purchases = purchaseService.getByBillId(billId);
        if(purchases==null) {
            throw new DataNotFoundException("Purchases list not found");
        }
        return purchases;
    }

    @GetMapping(value = "/purchases/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Purchase getPurchase(@PathVariable("id") int id) {
        purchaseExist(id);
        return purchaseService.getOne(id);
    }

    @PostMapping("/bills/{billId}/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public Purchase add(@PathVariable int billId, @RequestBody Purchase thePurchase) {
        if(purchaseService.exists(thePurchase.getId())) {
            throw new DataExistsException("Purchase with id '" + thePurchase.getId() + "' already exists");
        }
        Bill bill = billController.getBill(billId);
        thePurchase.setBill(bill);
        return purchaseService.add(thePurchase);
    }

    @PutMapping("/purchases/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Purchase updatePurchase( @RequestBody Purchase thePurchase ) {
        purchaseExist(thePurchase.getId());
        return purchaseService.update(thePurchase);
    }

    @DeleteMapping("/purchases/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePurchase( @PathVariable("id") int id ) {
        purchaseExist(id);
        purchaseService.delete(id);
    }

    private void purchaseExist(int id){
        if(!purchaseService.exists(id)) {
            throw new  DataNotFoundException("Purchase with Id = " + id + " not found ");
        }
    }
}
