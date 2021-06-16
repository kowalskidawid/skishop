package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
