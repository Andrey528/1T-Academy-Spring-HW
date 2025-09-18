package productServiceApp.controller;

import productServiceApp.dto.GetProductsRequest;
import productServiceApp.dto.GetProductsResponse;
import productServiceApp.dto.ProductDto;
import productServiceApp.dto.UpdateProductsRequest;
import productServiceApp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/byIds")
    public GetProductsResponse getProductsByUser(
            @RequestBody GetProductsRequest request
    ) {
        return productService.getProducts(request);
    }

    @PostMapping("/create")
    public ProductDto createProduct(
            @RequestParam(name = "userId") Long userId,
            @RequestBody ProductDto product
    ) {
        return productService.createProduct(userId, product);
    }

    @PutMapping("/update")
    public ProductDto updateProduct(
            @RequestBody ProductDto product) {
        return productService.updateProduct(product);
    }

    @PostMapping("/updateProductsBalance")
    public void updateProductsBalance(
            @RequestBody UpdateProductsRequest request
    ) {
        productService.updateProductsBalance(request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.deleteProduct(productId);
    }
}
