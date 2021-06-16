package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInDTO {
    private boolean logged;
    private UserDTO user;
    private String token;
}
