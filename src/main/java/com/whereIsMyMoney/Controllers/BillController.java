package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.BillDto;
import com.whereIsMyMoney.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
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
