package pl.kowalskidawid.skishop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchResultDTO {
    private Boolean matched;
    private String query;
    private List<CategoryDTO> categories;
    private List<ProductDTO> products;
}
