package pl.kowalskidawid.skishop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateOrderStatusDTO {
    private boolean updated;
    private String newStatus;
}
