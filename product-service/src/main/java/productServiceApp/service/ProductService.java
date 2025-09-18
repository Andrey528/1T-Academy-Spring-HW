package productServiceApp.service;

import productServiceApp.dto.GetProductsRequest;
import productServiceApp.dto.GetProductsResponse;
import productServiceApp.dto.ProductDto;
import productServiceApp.dto.UpdateProductsRequest;
import productServiceApp.exception.EmptyRequestException;
import productServiceApp.model.Product;
import productServiceApp.model.ProductDictionary;
import productServiceApp.model.User;
import productServiceApp.repository.ProductDictionaryRepository;
import productServiceApp.repository.ProductRepository;
import productServiceApp.util.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDictionaryRepository productDictionaryRepository;

    private final UserService userService;

    private final ProductMapper productMapper;

    public List<ProductDto> getProductsByUserId(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);

        if (products.isEmpty())
            throw new EntityNotFoundException("No product found for user id " + userId);

        return productMapper.toDTO(products);
    }

    public GetProductsResponse getProducts(GetProductsRequest request) {

        List<Product> productEntities = productRepository.findAllById(request.getProductIds());

        if (productEntities.isEmpty())
            throw new EntityNotFoundException("Products not found");

        List<ProductDto> productDtos = productMapper.toDTO(productEntities);

        return new GetProductsResponse(productDtos);
    }

    @Transactional
    public ProductDto createProduct(Long userId, ProductDto product) {

        User user = userService.getUser(userId);

        if (user == null)
            throw new EntityNotFoundException("User not found");

        ProductDictionary type = productDictionaryRepository.findById(product.getType())
                .orElseThrow(() -> new EntityNotFoundException("Product type not found"));

        Product newProduct = productMapper.toEntity(product);

        newProduct.setUser(user);
        newProduct.setType(type);

        newProduct = productRepository.save(newProduct);

        return productMapper.toDTO(newProduct);
    }

    @Transactional
    public void updateProductsBalance(UpdateProductsRequest request) {

        List<ProductDto> products = request.getProducts();

        if (products.isEmpty())
            throw new EmptyRequestException("Empty update product balance request");

        Map<Long, BigDecimal> idToBalanceMap = products.stream()
                .collect(Collectors.toMap(ProductDto::getProductId, ProductDto::getBalance));

        List<Product> productEntities = productRepository.findAllById(idToBalanceMap.keySet());

        if (productEntities.isEmpty())
            throw new EntityNotFoundException("Products not found");

        productEntities.forEach(product -> {
            if (idToBalanceMap.containsKey(product.getProductId())) {
                product.setBalance(idToBalanceMap.get(product.getProductId()));
                productRepository.save(product);
            }
        });
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
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return productMapper.toDTO(newProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
