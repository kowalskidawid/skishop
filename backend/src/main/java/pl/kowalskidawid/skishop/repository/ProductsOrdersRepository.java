package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.Product;
import pl.kowalskidawid.skishop.entity.ProductOrder;

import java.util.List;

@Repository
public interface ProductsOrdersRepository extends JpaRepository<ProductOrder, Integer> {
    public List<ProductOrder> findAllProductOrdersByOrderId(Integer id);
}
