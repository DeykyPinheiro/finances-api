package com.finances.domain.service;

import com.finances.domain.dto.user.UserDto;
import com.finances.domain.dto.user.UserListDto;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import com.finances.domain.exception.UserNotFoundException;
import com.finances.domain.model.User;
import com.finances.domain.repository.UserRepository;
import org.hibernate.mapping.UnionSubclass;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
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
        Page<UserListDto> pageUserListDto = page.map(user -> new UserListDto(user));
        return pageUserListDto;
    }

    public UserDto findById(long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException(userId));
        return new UserDto(user);
    }


    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }

    @Transactional
    public UserDto update(Long userId, UserUpdateDto userDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException(userId));
        User userUpdates = new User(userDto);

        modelMapper.map(userUpdates, user);
        return new UserDto(user);
    }
}
