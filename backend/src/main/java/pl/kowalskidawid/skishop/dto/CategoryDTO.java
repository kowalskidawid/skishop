package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {
    private Integer id;
    private String name;
    private String thumbnail;
    private Long count;
}
