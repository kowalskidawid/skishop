package pl.kowalskidawid.skishop.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kowalskidawid.skishop.repository.UsersRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UsersRepository usersRepository;

    public JwtUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            pl.kowalskidawid.skishop.entity.User user = this.usersRepository.findByEmail(email);
            return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
        } catch (Exception exception) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

}