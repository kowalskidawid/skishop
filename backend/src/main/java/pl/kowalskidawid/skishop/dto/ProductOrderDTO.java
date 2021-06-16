package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOrderDTO {
    private Integer quantity;
    private ProductDTO product;
}
