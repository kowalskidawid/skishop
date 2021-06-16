package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.Product;

import java.util.List;

@Repository
public interface ProductsRepository extends CrudRepository<Product, Integer> {
    List<Product> findProductByCategoryId(Integer categoryId);
    Long countProductByCategoryId(Integer categoryId);
    @Query("select p from product p where p.name like ?1 or p.description like ?1")
    List<Product> search(String query);
}
