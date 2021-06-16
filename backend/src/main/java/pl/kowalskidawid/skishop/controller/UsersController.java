package pl.kowalskidawid.skishop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import pl.kowalskidawid.skishop.Response;
import pl.kowalskidawid.skishop.dto.CreateUserDTO;
import pl.kowalskidawid.skishop.dto.UsersDTO;
import pl.kowalskidawid.skishop.entity.User;
import pl.kowalskidawid.skishop.entity.WebsiteError;
import pl.kowalskidawid.skishop.option.UserRule;
import pl.kowalskidawid.skishop.repository.UsersRepository;
import pl.kowalskidawid.skishop.repository.WebsiteErrorsRepository;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/users")
public class UsersController {
    private UsersRepository usersRepository;
    private WebsiteErrorsRepository websiteErrorsRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository, WebsiteErrorsRepository websiteErrorsRepository) {
        this.usersRepository = usersRepository;
        this.websiteErrorsRepository = websiteErrorsRepository;
    }

    @GetMapping(path = {"", "/", "/{_currentPage}/{_perPage}"})
    public ResponseEntity<UsersDTO> get(
            @PathVariable(required = false) Optional<Integer> _currentPage,
            @PathVariable(required = false) Optional<Integer> _perPage
    ) {
        int currentPage = 1;
        int perPage = 20;
        if (_currentPage.isPresent()) {
            currentPage = _currentPage.get();
        }
        if (_perPage.isPresent()) {
            perPage = _perPage.get();
        }
        Pageable page = PageRequest.of(currentPage - 1, perPage);
        int count = (int) this.usersRepository.count();
        UsersDTO dto = new UsersDTO(
                count,
                currentPage,
                perPage,
                (int) Math.ceil(count / (float) perPage),
                this.usersRepository.findAll(page).toList()
        );

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<CreateUserDTO> register(@Validated @RequestBody User newUser) {
        CreateUserDTO dto = new CreateUserDTO();
        try {
            if (this.usersRepository.countUserByEmail(newUser.getEmail()) > 0) {
                throw new EntityExistsException("Użytkownik z takim adresem email już istnieje!");
            }
            if (this.usersRepository.countUsersByPhone(newUser.getPhone()) > 0) {
                throw new EntityExistsException("Użytkownik z takim numerem telefonu już istnieje!");
            }

            newUser.setRule(UserRule.USER);
            User createdUser = this.usersRepository.save(newUser);
            dto.setCreated(true);
            dto.setResult(createdUser);
        } catch (EntityExistsException ex) {
            dto.setCreated(false);
            dto.setError(ex.getMessage());
        } catch (Exception ex) {
            dto.setCreated(false);
            this.websiteErrorsRepository.save(new WebsiteError("UsersController", "register", ex.getMessage(), null));
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/rule/{userId}")
    public ResponseEntity<?> updateRule(@PathVariable Integer userId, @RequestBody User userWithNewRule) {
        System.out.println(userWithNewRule.getRule());
        User user = this.usersRepository.findById(userId).get();
        if (userWithNewRule.getRule() == UserRule.USER) {
            user.setRule(UserRule.USER);
        } else if (userWithNewRule.getRule() == UserRule.SELLER) {
            user.setRule(UserRule.SELLER);
        } else if (userWithNewRule.getRule() == UserRule.ADMIN) {
            user.setRule(UserRule.ADMIN);
        }
        this.usersRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<?> delete(@PathVariable Integer userId) {
        this.usersRepository.deleteById(userId);
        return ResponseEntity.ok(true);
    }
}