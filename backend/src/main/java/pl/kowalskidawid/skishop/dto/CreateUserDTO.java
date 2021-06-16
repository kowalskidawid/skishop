package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateUserDTO {
    private Boolean created;
    private User result;
    private String error;
}
