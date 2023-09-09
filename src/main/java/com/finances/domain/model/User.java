package com.finances.domain.model;

import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private Date birthDate;

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
}
