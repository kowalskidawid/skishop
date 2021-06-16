package pl.kowalskidawid.skishop.dto;


import lombok.*;
import pl.kowalskidawid.skishop.entity.Product;
import pl.kowalskidawid.skishop.entity.User;
import pl.kowalskidawid.skishop.option.OrderStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderOnListDTO {
    private Integer id;
    private OrderStatus status;
    private Double totalPrice;
    private User user;
    private List<ProductOrderDTO> products;
    private String timestamp;
}
