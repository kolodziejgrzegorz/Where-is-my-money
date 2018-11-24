package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.PurchaseDto;
import com.whereIsMyMoney.service.PurchaseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/list")
    public List<PurchaseDto> getAllPurchases() {
        return purchaseService.findAll();
    }

    @GetMapping("/bills/{billId}/list")
    public List<PurchaseDto> getBillPurchases(@PathVariable Long billId) {
        return purchaseService.findByBillId(billId);
    }

    @GetMapping("/list/{id}")
    public PurchaseDto getPurchase(@PathVariable("id") Long id) {
        return purchaseService.findById(id);
    }
//No need for this methods

//    @PostMapping("/bills/{billId}/list")
//    @ResponseStatus(HttpStatus.CREATED)
//    public PurchaseDto addNew(@PathVariable Long billId, @RequestBody PurchaseDto thePurchaseDto) {
//        return purchaseService.addNew(billId, thePurchaseDto);
//    }
//
//    @PutMapping("/bills/{billId}/list")
//    public PurchaseDto updatePurchase(@PathVariable Long billId, @RequestBody PurchaseDto thePurchaseDto) {
//        return purchaseService.update(billId, thePurchaseDto);
//    }
//
//    @DeleteMapping("/list/{id}")
//    public void deletePurchase( @PathVariable("id") Long id ) {
//        purchaseService.delete(id);
//    }
}
