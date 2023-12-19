package com.finances.api.controller;

import com.finances.domain.dto.user.UserDto;
import com.finances.domain.dto.user.UserListDto;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import com.finances.domain.exception.UserNotFoundException;
import com.finances.domain.model.User;
import com.finances.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserSaveDto userDto) {
        UserDto user = userService.save(userDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + user.id());
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<UserListDto>> list(Pageable pageable) {
        return new ResponseEntity(userService.list(pageable), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        return new ResponseEntity(userService.findById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable Long userId, @RequestBody @Valid UserUpdateDto userDto) {
        return new ResponseEntity(userService.update(userId, userDto), HttpStatus.OK);
    }
}

