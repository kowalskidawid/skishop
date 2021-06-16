package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionDTO {
    private Integer code;
    private String message;
}
