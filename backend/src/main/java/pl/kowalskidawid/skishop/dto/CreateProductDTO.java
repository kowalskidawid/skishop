package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateProductDTO {
    private Boolean created;
    private ProductDTO result;
    private String error;
}
