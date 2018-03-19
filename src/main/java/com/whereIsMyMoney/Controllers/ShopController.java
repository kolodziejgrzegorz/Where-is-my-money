package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.dataModel.Shop;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {
    @Autowired
    private ShopService shopService;

    public ShopController() {
    }

    @GetMapping("/shops")
    @ResponseStatus(HttpStatus.OK)
    public List<Shop> getAllShops() {
        List<Shop> shops = shopService.getAll();
        if(shops==null) {
            throw new DataNotFoundException("Shops list not found");
        }
        return shops;
    }

    @GetMapping(value = "/shops/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Shop getShop(@PathVariable("id") int id) {
        shopExist(id);
        return shopService.getOne(id);
    }

    @RequestMapping( value = "/shops", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Shop theShop) {
        if(shopService.exists(theShop.getId())) {
            throw new DataExistsException("Shop with id '" + theShop.getId() + "' already exists");
        }
        shopService.add(theShop);
    }

    @PutMapping( "/shops/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateShop(@RequestBody Shop theShop) {
        shopExist(theShop.getId());
        shopService.update(theShop);
    }

    @DeleteMapping("/shops/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShop(@PathVariable("id") int id) {
        shopExist(id);
        shopService.delete(id);
    }

    private void shopExist(int id){
        if(!shopService.exists(id)) {
            throw new  DataNotFoundException("Shop with Id = " + id + " not found ");
        }
    }
}
