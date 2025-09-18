package productServiceApp.util;

import productServiceApp.dto.ProductDto;
import productServiceApp.model.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductMapper {
    public List<ProductDto> toDTO(List<Product> products) {

        if (products == null || products.isEmpty()) return Collections.emptyList();

        return products.stream().map(this::toDTO).toList();
    }

    public ProductDto toDTO(Product product) {
        if (product == null) return null;

        return new ProductDto(
                product.getProductId(),
                product.getAccNumber(),
                product.getBalance(),
                product.getType().getCode()
        );
    }

    public Product toEntity(ProductDto productDto) {
        return new Product(
                productDto.getAccNumber(),
                productDto.getBalance()
        );
    }
}
