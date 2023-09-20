package com.finances.domain.model;

import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private Date birthDate;


    @OneToMany
    private Set<Account> lisAccounts = new HashSet<>();

    @CreationTimestamp
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    private OffsetDateTime dateUpdate;

    public User(Long id, String name, String password, Date birthDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
    }

    public User(UserSaveDto userDto) {
        this.name = userDto.name();
        this.password = userDto.password();
        this.email = userDto.email();
        this.birthDate = userDto.birthDate();
    }

    public User(UserUpdateDto userDto) {
        this.name = userDto.name();
        this.email = userDto.email();
        this.birthDate = userDto.birthDate();
    }

    public User(long userId) {
        this.id = userId;
    }

    public User(long userId, String name, String password, String email, Date birthDate) {
        this.id = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
    }

    //    TODO
//    implements groups and permissions
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
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
