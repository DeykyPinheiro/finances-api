package com.finances.domain.service;

import com.finances.domain.dto.user.UserDto;
import com.finances.domain.dto.user.UserListDto;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import com.finances.domain.exception.UserNotFoundException;
import com.finances.domain.model.User;
import com.finances.domain.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.mapping.UnionSubclass;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;


    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //    TODO
//    add negotiation rule of not including duplicate and by an encrypted password
    @Transactional
    public UserDto save(@NotNull @Valid UserSaveDto userDto) {
        User user = new User(userDto);
        String encondedPassword = passwordEncoder.encode(userDto.password());
        user.setPassword(encondedPassword);
        user = userRepository.save(user);
        return new UserDto(user);
    }

    public Page<UserListDto> list(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        Page<UserListDto> pageUserListDto = page.map(user -> new UserListDto(user));
        return pageUserListDto;
    }

    public UserDto findById(@NotNull long userId) {
        User user = this.findById(new User(userId));
        return new UserDto(user);
    }

    public User findById(@Valid @NotNull User user) {
        return userRepository.findById(user.getId()).
                orElseThrow(() -> new UserNotFoundException(user.getId()));
    }

    @Transactional
    public void delete(@NotNull Long userId) {
        User user = findById(new User(userId));
        userRepository.delete(user);
    }

    @Transactional
    public UserDto update(@NotNull Long userId, @Valid @NotNull UserUpdateDto userDto) {
        User user = findById(new User(userId));
        User userUpdates = new User(userDto);

        modelMapper.map(userUpdates, user);
        return new UserDto(user);
    }
}
