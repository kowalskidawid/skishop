package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.ProductProperty;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private Integer id;
    private Double price;
    private String name;
    private String description;
    private CategoryDTO category;
    private List<ProductProperty> properties;
    private List<ProductDTO> relatedProducts;
    private Integer inStock;
}
