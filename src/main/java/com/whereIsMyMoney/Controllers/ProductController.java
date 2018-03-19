package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.dataModel.Product;
import com.whereIsMyMoney.exception.DataExistsException;
import com.whereIsMyMoney.exception.DataNotFoundException;
import com.whereIsMyMoney.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAll();
        if(products==null) {
            throw new DataNotFoundException("Products list not found");
        }
        return products;
    }

    @GetMapping(value = "/products/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable("id") int id) {
        if(!productService.exists(id)) {
            throw new  DataNotFoundException("Product with Id = " + id + " not found ");
        }
        return productService.getOne(id);
    }

    @RequestMapping( value = "/products", method = RequestMethod.POST )
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Product theProduct) {
        if(productService.exists(theProduct.getId())) {
            throw new DataExistsException("Product with id '" + theProduct.getId() + "' already exists");
        }
        productService.add(theProduct);

    }

    @PutMapping( "/products/{id}" )
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product theProduct) {
        if(!productService.exists(theProduct.getId())) {
            throw new  DataNotFoundException("Product with Id = " + theProduct.getId() + " not found ");
        }
        productService.update(theProduct);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("id") int id) {
        if(!productService.exists(id)) {
            throw new  DataNotFoundException("Product with Id = " + id + " not found ");
        }
        productService.delete(id);
    }

}
