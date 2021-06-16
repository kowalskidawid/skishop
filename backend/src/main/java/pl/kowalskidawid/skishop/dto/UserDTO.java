package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.option.UserRule;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private Integer id;
    private String email;
    private String rule;
    private String phone;
    private String firstName;
    private String lastName;

}
