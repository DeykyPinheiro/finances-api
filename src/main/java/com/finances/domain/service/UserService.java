package com.finances.domain.service;

import com.finances.domain.dto.user.UserDto;
import com.finances.domain.dto.user.UserListDto;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.model.User;
import com.finances.domain.repository.UserRepository;
import org.hibernate.mapping.UnionSubclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto save(UserSaveDto userDto) {
        User user = new User(userDto);
        user = userRepository.save(user);
        return new UserDto(user);
    }

    public Page<UserListDto> list(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<UserListDto> listUserDto = UserListDto.convertToList(page.getContent());
        Page<UserListDto> pageUserListDto = new PageImpl<>(listUserDto, pageable, page.getTotalElements());
        return pageUserListDto;
    }
}
