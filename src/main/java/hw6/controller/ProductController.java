package hw6.controller;

import hw6.dto.ProductDto;
import hw6.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/user/{userId}")
    public List<ProductDto> getProductsByUser(@PathVariable(name = "userId") Long userId) {
        return productService.getProductsByUserId(userId);
    }

    @PostMapping("/create")
    public ProductDto createProduct(
            @RequestParam(name = "userId") Long userId,
            @RequestBody ProductDto product
    ) {
        return productService.createProduct(userId, product);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto product) {
        try {
            ProductDto updatedProduct = productService.updateProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.deleteProduct(productId);
    }
}
