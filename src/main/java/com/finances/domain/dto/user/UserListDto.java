package com.finances.domain.dto.user;

import com.finances.domain.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record UserListDto(

        Long id,

        String name,

        String email,

        @DateTimeFormat
        Date birthDate) {

    public UserListDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getBirthDate());
    }


    public static List<UserListDto> convertToList(Collection<User> listUser) {
        return listUser.stream().map(UserListDto::new).collect(Collectors.toList());
    }
}
