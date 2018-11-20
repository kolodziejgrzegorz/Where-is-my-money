package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.domain.Bill;
import com.whereIsMyMoney.domain.Purchase;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final BillController billController;

    public PurchaseController(PurchaseService purchaseService, BillController billController) {
        this.purchaseService = purchaseService;
        this.billController = billController;
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
    public List<Purchase> getBillPurchases(@PathVariable Long billId) {
        Bill bill = billController.getBill(billId);
        List<Purchase> purchases = purchaseService.getByBillId(billId);
        if(purchases==null) {
            throw new DataNotFoundException("Purchases list not found");
        }
        return purchases;
    }

    @GetMapping(value = "/purchases/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Purchase getPurchase(@PathVariable("id") Long id) {
        purchaseExist(id);
        return purchaseService.findById(id);
    }

    @PostMapping("/bills/{billId}/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public Purchase add(@PathVariable Long billId, @RequestBody Purchase thePurchase) {
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
    public void deletePurchase( @PathVariable("id") Long id ) {
        purchaseExist(id);
        purchaseService.delete(id);
    }

    private void purchaseExist(Long id){
        if(!purchaseService.exists(id)) {
            throw new  DataNotFoundException("Purchase with Id = " + id + " not found ");
        }
    }
}
