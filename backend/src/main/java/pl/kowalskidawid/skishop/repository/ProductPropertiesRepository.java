package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.Product;
import pl.kowalskidawid.skishop.entity.ProductProperty;

import java.util.List;

@Repository
public interface ProductPropertiesRepository extends JpaRepository<ProductProperty, Integer> {
}
