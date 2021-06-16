package pl.kowalskidawid.skishop.dto;

import lombok.*;
import pl.kowalskidawid.skishop.entity.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersDTO {
    private Integer count;
    private Integer currentPage;
    private Integer perPage;
    private Integer maxPage;
    private List<User> users;
}
