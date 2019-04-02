package com.pyy.spring.jpa.controller;

import com.pyy.spring.jpa.model.Product;
import com.pyy.spring.jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/product")
    public List<Product> getAllProduct(){
        return productService.getAllProducts();
    }


}
