package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.Product;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsDTO {
    private Long count;
    private Long currentPage;
    private Long maxPage;
    private Long perPage;
    private String searchQuery;
    private Iterator<Product> products;
}
