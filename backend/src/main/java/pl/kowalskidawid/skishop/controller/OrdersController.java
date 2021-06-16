package pl.kowalskidawid.skishop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kowalskidawid.skishop.dto.*;
import pl.kowalskidawid.skishop.entity.Order;
import pl.kowalskidawid.skishop.entity.Product;
import pl.kowalskidawid.skishop.entity.ProductOrder;
import pl.kowalskidawid.skishop.entity.WebsiteError;
import pl.kowalskidawid.skishop.exeption.NotEnoughInStockException;
import pl.kowalskidawid.skishop.option.OrderStatus;
import pl.kowalskidawid.skishop.repository.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/orders")
public class OrdersController {
    private WebsiteErrorsRepository websiteErrorsRepository;
    private OrdersRepository ordersRepository;
    private ProductsRepository productsRepository;
    private ProductsOrdersRepository productsOrdersRepository;
    private UsersRepository usersRepository;

    public OrdersController(WebsiteErrorsRepository websiteErrorsRepository, OrdersRepository ordersRepository, ProductsRepository productsRepository, ProductsOrdersRepository productsOrdersRepository, UsersRepository usersRepository) {
        this.websiteErrorsRepository = websiteErrorsRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.productsOrdersRepository = productsOrdersRepository;
        this.usersRepository = usersRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(Authentication authentication, @RequestBody Map<Integer, Integer> products) {
        OrderDTO dto = new OrderDTO();
        try {
            Order order = new Order();
            order.setStatus(OrderStatus.PLACED_ORDER);
            order.setUsers(this.usersRepository.findByEmail(authentication.getName()));
            this.ordersRepository.save(order);
            Double totalPrice = 0D;
            for (Map.Entry<Integer, Integer> orderProduct : products.entrySet()) {
                Product product = this.productsRepository.findById(orderProduct.getKey()).get();
                if (product.getInStock() < orderProduct.getValue()) {
                    String message = "Nie wystarczająca ilość " + product.getName() + " w magazynie!";
                    if (product.getInStock() > 0) {
                        message += " Możesz zamówić maksymalnie " + product.getInStock() + " sztuk.";
                    }
                    this.ordersRepository.delete(order);
                    throw new NotEnoughInStockException(message);
                }
                product.setInStock(product.getInStock() - orderProduct.getValue());
                this.productsRepository.save(product);
                ProductOrder productInOrder = new ProductOrder(product, order, orderProduct.getValue());
                this.productsOrdersRepository.save(productInOrder);
                totalPrice += product.getPrice() * orderProduct.getValue();
            }
            order.setTotalPrice(totalPrice);
            this.ordersRepository.save(order);
            dto.setOrderId(order.getId());
            dto.setOrdered(true);
        } catch (NotEnoughInStockException exception) {
            this.websiteErrorsRepository.save(new WebsiteError("OrdersController", "create", exception.getMessage(), null));
            dto.setError(exception.getMessage());
            dto.setOrdered(false);
        } catch (Exception exception) {
            this.websiteErrorsRepository.save(new WebsiteError("OrdersController", "create", exception.getMessage(), null));
            dto.setError(exception.getMessage());
            dto.setOrdered(false);
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<OrdersDTO> list() {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setCount(this.ordersRepository.count());
        List<OrderOnListDTO> listOfOrderOnList = new ArrayList<>();
        this.ordersRepository.findAll().forEach(order -> {
            OrderOnListDTO orderOnListDTO = new OrderOnListDTO();
            orderOnListDTO.setUser(order.getUsers());
            orderOnListDTO.setId(order.getId());
            orderOnListDTO.setTotalPrice(order.getTotalPrice());
            orderOnListDTO.setStatus(order.getStatus());
            orderOnListDTO.setTimestamp(order.getTimestamp());
            List<ProductOrderDTO> productsList = new ArrayList<>();
            this.productsOrdersRepository.findAllProductOrdersByOrderId(order.getId()).forEach(productOrder -> {
                Product orderedProduct = productOrder.getProduct();
                productsList.add(new ProductOrderDTO(productOrder.getQuantity(), new ProductDTO(orderedProduct.getId(), orderedProduct.getPrice(), orderedProduct.getName(), orderedProduct.getDescription(), null, orderedProduct.getProperties(), null, orderedProduct.getInStock())));
            });
            orderOnListDTO.setProducts(productsList);
            listOfOrderOnList.add(orderOnListDTO);
        });
        ordersDTO.setOrders(listOfOrderOnList);
        return ResponseEntity.ok(ordersDTO);
    }

    @PutMapping(path = "/{orderId}")
    public ResponseEntity<?> update(@PathVariable Integer orderId, @RequestBody Order orderWithNewStatus) {
        try {
            Order order = this.ordersRepository.findById(orderId).get();
            order.setStatus(orderWithNewStatus.getStatus());
            this.ordersRepository.save(order);
            return ResponseEntity.ok(new UpdateOrderStatusDTO(true, order.getStatus().toString()));
        } catch (Exception exception) {
            this.websiteErrorsRepository.save(new WebsiteError("OrdersController", "update", exception.getMessage(), null));
            return ResponseEntity.ok(new ExceptionDTO(404, "Nie znaleziono zamówienia"));
        }
    }
}
