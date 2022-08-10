package com.example.nguyenhoanganh.api;

import com.example.nguyenhoanganh.entity.Product;
import com.example.nguyenhoanganh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(path = "api/v1/products")
@RestController
@CrossOrigin("*")
public class ProductApi {
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> findAll(){
        return productService.findAll();
    }
    @RequestMapping(method = RequestMethod.GET, path = {"id"})
    public ResponseEntity<?> findById(@PathVariable String id){
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product Product){
        return ResponseEntity.ok(productService.save(Product));
    }
    @RequestMapping(method = RequestMethod.DELETE, path = {"id"})
    public ResponseEntity<?> deleteById(@PathVariable String id){
        if (!productService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(method = RequestMethod.PUT, path = {"id"})
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product updateProduct) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        Product existProduct = optionalProduct.get();

        existProduct.setName(updateProduct.getName());
        existProduct.setPrice(updateProduct.getPrice());
        existProduct.setQty(updateProduct.getQty());

        return ResponseEntity.ok(productService.save(existProduct));
    }

}
