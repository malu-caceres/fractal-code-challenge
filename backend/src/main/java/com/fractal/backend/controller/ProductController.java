package com.fractal.backend.controller;

import com.fractal.backend.exception.ProductNotFoundException;
import com.fractal.backend.model.Product;
import com.fractal.backend.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    Product newProduct(@RequestBody Product newUser) {
        return productRepository.save(newUser);
    }

    @GetMapping("/products")
    List<Product> getAllUsers() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    Product getUserById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/products/{id}")
    Product updateUser(@RequestBody Product newUser, @PathVariable Long id) {
        return productRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUnitPrice(newUser.getUnitPrice());
                    user.setStock(newUser.getStock());
                    return productRepository.save(user);
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/products/{id}")
    String deleteUser(@PathVariable Long id){
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return  "Product with id "+id+" has been deleted success.";
    }

}
