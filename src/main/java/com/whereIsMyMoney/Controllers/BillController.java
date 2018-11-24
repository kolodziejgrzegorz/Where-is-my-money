package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.mapper.BillMapper;
import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.service.BillService;
import com.whereIsMyMoney.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BillController {

    private final BillService billService;
    private final PurchaseService purchaseService;
    private final BillMapper billMapper;

    public BillController(BillService billService, PurchaseService purchaseService, BillMapper billMapper) {
        this.billService = billService;
        this.purchaseService = purchaseService;
        this.billMapper = billMapper;
    }

    @GetMapping("/bills")
    public List<BillDto> getAllBills() {
         return billService.findAll();
    }

    @GetMapping("/bills/{id}")
    public BillDto getBill(@PathVariable Long id ) {
        return billService.findById(id);
    }

    @PostMapping("/bills")
    @ResponseStatus(HttpStatus.CREATED)
    public BillDto addNew(@RequestBody BillDto theBillDto) {
        return billService.addNew(theBillDto);
    }
//logic move to billService
//    @PostMapping("/bills/{id}/list")
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<String> addPurchasesToBill(@PathVariable Long id, @RequestBody PurchaseList purchaseList){
//        List<String> response = new ArrayList<>();
//        for(Purchase purchase: purchaseList.getList() ){
//            purchaseService.add(purchase);
//            response.add(purchase.toString());
//        }
//        return response;
//    }

    @PutMapping("/bills/{id}" )
    @ResponseStatus(HttpStatus.CREATED)
    public BillDto updateBill(@RequestBody BillDto theBillDto, @PathVariable Long id) {
        return billService.update(id ,theBillDto);
    }

    @DeleteMapping("/bills/{id}")
    public void deleteBill(@PathVariable("id") Long id) {
        billService.delete(id);
    }
}
