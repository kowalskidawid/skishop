package pl.kowalskidawid.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kowalskidawid.skishop.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String username);

    public Integer countUserByEmail(String email);
    public Integer countUsersByPhone(String phone);
}
