package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteCategoryDTO {
    private Boolean deleted;
    private String error;
}
