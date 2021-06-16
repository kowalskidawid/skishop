package pl.kowalskidawid.skishop.entity;

import org.hibernate.annotations.Fetch;
import pl.kowalskidawid.skishop.option.UserRule;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    public Integer id;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule")
    public UserRule rule;

    @Column(name = "phone", nullable = false, unique = true)
    public String phone;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Address address;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Enum<UserRule> getRule() {
        return rule;
    }

    public void setRule(UserRule rule) {
        this.rule = rule;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
