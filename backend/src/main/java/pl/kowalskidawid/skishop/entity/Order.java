package pl.kowalskidawid.skishop.entity;

import lombok.Builder;
import pl.kowalskidawid.skishop.option.OrderStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "timestamp")
    private String timestamp;

    public Order() {
    }

    public Order(Integer id, OrderStatus status, Double totalPrice, User user, Set<Product> products, String timestamp) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
