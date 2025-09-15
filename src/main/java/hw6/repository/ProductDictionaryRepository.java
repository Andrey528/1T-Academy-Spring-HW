package hw6.repository;

import hw6.model.ProductDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDictionaryRepository extends JpaRepository<ProductDictionary, String> {
}
