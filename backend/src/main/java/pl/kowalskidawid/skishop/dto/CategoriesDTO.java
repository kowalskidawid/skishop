package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.Category;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoriesDTO {
    private Long count;
    private List<CategoryDTO> categories;
}
