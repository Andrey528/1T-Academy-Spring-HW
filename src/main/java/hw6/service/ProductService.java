package hw6.service;

import hw6.dto.ProductDto;
import hw6.model.Product;
import hw6.model.ProductDictionary;
import hw6.model.User;
import hw6.repository.ProductDictionaryRepository;
import hw6.repository.ProductRepository;
import hw6.util.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDictionaryRepository productDictionaryRepository;

    private final UserService userService;

    public List<ProductDto> getProductsByUserId(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);

        return ProductMapper.toDTO(products);
    }

    @Transactional
    public ProductDto createProduct(Long userId, ProductDto product) {

        User user = userService.getUser(userId);

        if (user == null)
            throw new EntityNotFoundException("User not found");

        ProductDictionary type = productDictionaryRepository.findById(product.getType())
                .orElseThrow(() -> new EntityNotFoundException("Product type not found"));

        Product newProduct = ProductMapper.toEntity(product);

        newProduct.setUser(user);
        newProduct.setType(type);

        newProduct = productRepository.save(newProduct);

        return ProductMapper.toDTO(newProduct);
    }

    @Transactional
    public ProductDto updateProduct(ProductDto updatedProduct) {

        ProductDictionary type = productDictionaryRepository.findById(updatedProduct.getType())
                .orElseThrow(() -> new EntityNotFoundException("Product type not found"));

        Product newProduct = productRepository.findById(updatedProduct.getProductId())
                .map(product -> {
                    if (!product.getAccNumber().equals(updatedProduct.getAccNumber()))
                        product.setAccNumber(updatedProduct.getAccNumber());

                    if (!product.getBalance().equals(updatedProduct.getBalance()))
                        product.setBalance(updatedProduct.getBalance());

                    if (!product.getType().equals(type))
                        product.setType(type);

                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toDTO(newProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
