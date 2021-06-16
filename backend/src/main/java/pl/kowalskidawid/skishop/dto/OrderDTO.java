package pl.kowalskidawid.skishop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private Boolean ordered;
    private Integer orderId;
    private List<ProductDTO> products;
    private String error;
}
