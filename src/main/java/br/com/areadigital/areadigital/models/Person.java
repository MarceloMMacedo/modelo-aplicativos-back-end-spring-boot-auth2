package br.com.areadigital.areadigital.models;

import br.com.areadigital.areadigital.models.add.Contato;
import br.com.areadigital.areadigital.models.add.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "tb_user")
public class Person implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;
    private String lastname;
    @Column(unique = true)
//    @NotBlank(message = "Email Obrigatório")
    private String email;
    private String password;

    @Embedded
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Column(name = "id")
    private Contato contato ;

    @Embedded
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Column(name = "id")
    private Endereco endereco  ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .toList();
    }

    @Override
    public String getPassword() {
        
        return this.password;
    }

    @Override
    public String getUsername() {
        
        return email;
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
    public boolean isEnabled() {
        return true;
    }
}
