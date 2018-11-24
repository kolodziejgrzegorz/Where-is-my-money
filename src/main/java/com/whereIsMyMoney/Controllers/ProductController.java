package com.whereIsMyMoney.Controllers;

import com.whereIsMyMoney.api.model.ProductDto;
import com.whereIsMyMoney.domain.Product;
import com.whereIsMyMoney.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public ProductDto getProduct(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addNew(@RequestBody ProductDto theProductDto) {
        return productService.addNew(theProductDto);
    }

    @PutMapping( "/products/{id}" )
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto updateProduct(@RequestBody ProductDto theProductDto) {
        return productService.update(theProductDto);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    //todo
    @GetMapping(value = "/products/search", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Product> getByNameStartingWith(@RequestParam("q") String input) {
        List<Product> products = productService.getByNameStartingWith(input);
        return products;
    }

}
