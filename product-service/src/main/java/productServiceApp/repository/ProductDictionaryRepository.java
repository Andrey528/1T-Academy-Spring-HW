package productServiceApp.repository;

import productServiceApp.model.ProductDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDictionaryRepository extends JpaRepository<ProductDictionary, String> {
}
