package pl.kowalskidawid.skishop.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdersDTO {
    private Long count;
    private List<OrderOnListDTO> orders;
}
