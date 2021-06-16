package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.Category;
import pl.kowalskidawid.skishop.entity.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsInCategoryDTO {
    private Long count;
    private Long currentPage;
    private Long maxPage;
    private Long perPage;
    private String searchQuery;
    private Category category;
    private List<Product> products;
}
