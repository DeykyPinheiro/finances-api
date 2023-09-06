package com.finances.domain.dto.user;

import com.finances.domain.model.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record UserListDto(
        String name,

        String email,

        Date birthDate) {

    public UserListDto(User user) {
        this(user.getName(), user.getEmail(), user.getBirthDate());
    }


    public static List<UserListDto> convertToList(Collection<User> listUser) {
        return listUser.stream().map(UserListDto::new).collect(Collectors.toList());
    }
}
