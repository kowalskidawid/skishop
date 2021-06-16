package pl.kowalskidawid.skishop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.kowalskidawid.skishop.dto.SignInDTO;
import pl.kowalskidawid.skishop.dto.UserDTO;
import pl.kowalskidawid.skishop.entity.JwtRequest;
import pl.kowalskidawid.skishop.entity.User;
import pl.kowalskidawid.skishop.repository.UsersRepository;
import pl.kowalskidawid.skishop.service.JwtTokenUtil;

import javax.security.auth.login.CredentialException;

@CrossOrigin
@RestController
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService jwtInMemoryUserDetailsService;
    private final UsersRepository usersRepository;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, UserDetailsService jwtInMemoryUserDetailsService, UsersRepository usersRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
        this.jwtTokenUtil = new JwtTokenUtil();
        this.usersRepository = usersRepository;
    }

    @RequestMapping(value = "/api/authorize", method = RequestMethod.POST)
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        UserDTO userDTO = new UserDTO();
        boolean logged = true;
        String token = null;
        try {
            User user = this.usersRepository.findByEmail(authenticationRequest.getEmail());
            if (!user.getPassword().equals(authenticationRequest.getPassword())) {
                throw new CredentialException("Invalid credentials");
            }
            token = jwtTokenUtil.generateJwtToken(user);
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setRule(user.getRule().toString());
            userDTO.setPhone(user.getPhone());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
        } catch (Exception exception) {
            logged = false;
            token = exception.getMessage();
        }

        return ResponseEntity.ok(new SignInDTO(logged, userDTO, token));
    }

    private void authenticate(String username, String password) throws Exception {
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
    }
}
