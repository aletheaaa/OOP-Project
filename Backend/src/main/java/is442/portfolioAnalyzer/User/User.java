package is442.portfolioAnalyzer.User;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import is442.portfolioAnalyzer.Asset.Asset;
import is442.portfolioAnalyzer.Portfolio.Portfolio;
import is442.portfolioAnalyzer.config.ApplicationConfig;
import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Data // Generate Getter and Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name="users")

public class User implements UserDetails { // UserDetails contains methods from user security
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)  // id will automatically be generated. By default, is auto
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    private String password;


    @OneToMany
    // @JoinColumn(name = "id", referencedColumnName = "id")
    private List<Portfolio> portfolios;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    public String getLastname() {
        return lastName;
    }

    @JsonIgnore
    public String getFirstname() {
        return firstName;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled()  {
        return true;
    }
}

@Data
class ChangePasswordRequest {
    private String email;

    private String currentPassword;
    private String newPassword;


    public String getEmail() {
        return email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
