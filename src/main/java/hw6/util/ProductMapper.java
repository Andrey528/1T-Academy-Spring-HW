package hw6.util;

import hw6.dto.ProductDto;
import hw6.model.Product;

import java.util.Collections;
import java.util.List;

public class ProductMapper {
    public static List<ProductDto> toDTO(List<Product> products) {

        if (products == null || products.isEmpty()) return Collections.emptyList();

        return products.stream().map(ProductMapper::toDTO).toList();
    }

    public static ProductDto toDTO(Product product) {
        if (product == null) return null;

        return new ProductDto(
                product.getProductId(),
                product.getAccNumber(),
                product.getBalance(),
                product.getType().getCode()
        );
    }

    public static Product toEntity(ProductDto productDto) {
        return new Product(
                productDto.getAccNumber(),
                productDto.getBalance()
        );
    }
}
