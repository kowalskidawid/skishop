package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateCategoryDTO {
    private Boolean created;
    private CategoryDTO result;
    private String error;
}
