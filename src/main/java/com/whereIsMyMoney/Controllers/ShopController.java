package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.ShopDto;
import com.whereIsMyMoney.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shops")
    public List<ShopDto> getAllShops() {
        return shopService.getAll();
    }

    @GetMapping("/shops/{id}")
    public ShopDto getShop(@PathVariable("id") Long id) {
        return shopService.findById(id);
    }

    @PostMapping("/shops")
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDto add(@RequestBody ShopDto theShopDto) {
        return shopService.addNew(theShopDto);
    }

    @PutMapping( "/shops/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDto updateShop(@RequestBody ShopDto theShopDto) {
        return shopService.update(theShopDto);
    }

    @DeleteMapping("/shops/{id}")
    public void deleteShop(@PathVariable("id") Long id) {
        shopService.delete(id);
    }
}
