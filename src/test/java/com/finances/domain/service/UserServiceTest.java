package com.finances.domain.service;

import com.finances.domain.dto.user.UserDto;
import com.finances.domain.dto.user.UserListDto;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import com.finances.domain.exception.UserNotFoundException;
import com.finances.domain.model.User;
import com.finances.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

//@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        userService = new UserService(userRepository, modelMapper, null);
    }


    @Test
    void shouldReturnUserDto_WhenSaveCorrectly() {
        Long userId = 1L;
        Date date = new Date();
        User userSaveRepositoryDto = new User(userId, "Deyky", "123456", "teste@teste.com", date);
        UserDto userExpectedServiceDto = new UserDto(userId, "Deyky", "teste@teste.com", date);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userSaveRepositoryDto);
        UserSaveDto userSaveServicedto = new UserSaveDto("Deyky", "123456", "teste@teste.com", date);

        UserDto userResposeDto = userService.save(userSaveServicedto);

        Assertions.assertEquals(userExpectedServiceDto, userResposeDto);
    }

    @Test
    void shouldReturnNullPointerException_WhenCreateUserWithoutPassword() {
        Date date = new Date();
        UserSaveDto userSaveServicedto = new UserSaveDto("Deyky", "", "teste@teste.com", date);

        NullPointerException error =
                Assertions.assertThrows(NullPointerException.class, () -> {
                    userService.save(userSaveServicedto);
                });

        Assertions.assertTrue(error instanceof NullPointerException);
    }

    @Test
    void shouldReturnNullPointerException_WhenCreateUserWithoutName() {
        Date date = new Date();
        UserSaveDto userSaveServicedto = new UserSaveDto("", "123456", "teste@teste.com", date);

        NullPointerException error =
                Assertions.assertThrows(NullPointerException.class, () -> {
                    userService.save(userSaveServicedto);
                });

        Assertions.assertTrue(error instanceof NullPointerException);
    }

    @Test
    void shouldReturnNullPointerException_WhenCreateUserWithoutEmail() {
        Date date = new Date();
        UserSaveDto userSaveServicedto = new UserSaveDto("deyky", "123456", "", date);

        NullPointerException error =
                Assertions.assertThrows(NullPointerException.class, () -> {
                    userService.save(userSaveServicedto);
                });

        Assertions.assertTrue(error instanceof NullPointerException);
    }

    @Test
    void shouldReturnNullPointerException_WhenCreateUserWithInvalidEmail() {
        Date date = new Date();
        UserSaveDto userSaveServicedto = new UserSaveDto("deyky", "123456", "testeteste.com", date);

        NullPointerException error =
                Assertions.assertThrows(NullPointerException.class, () -> {
                    userService.save(userSaveServicedto);
                });

        Assertions.assertTrue(error instanceof NullPointerException);
    }

    @Test
    void shouldReturnNullPointerException_WhenCreateUserWithDate() {
        UserSaveDto userSaveServicedto = new UserSaveDto("deyky", "123456", "testeteste.com", null);

        NullPointerException error =
                Assertions.assertThrows(NullPointerException.class, () -> {
                    userService.save(userSaveServicedto);
                });

        Assertions.assertTrue(error instanceof NullPointerException);
    }


    @Test
    void shouldReturnListUserDto_WhenListCorrectly() {
        Long userId = 1L;
        Date date = new Date();
        User user = new User(userId, "Deyky", "123456", "teste@teste.com", date);
        Pageable pageable = PageRequest.of(0, 20);
        Page<User> pageUsers = new PageImpl<>(List.of(user));
        Mockito.when(userRepository.findAll(pageable)).thenReturn(pageUsers);
        Page<UserListDto> ExpectedPage = new PageImpl<>(List.of(new UserListDto(user)));

        Page<UserListDto> pageRespose = userService.list(pageable);

        Assertions.assertEquals(ExpectedPage, pageRespose);
    }

    @Test
    void shouldReturnUserDto_WhenFindByUserWithValidId() {
        Long userId = 1L;
        Date date = new Date();
        User user = new User(userId, "Deyky", "123456", "teste@teste.com", date);
        UserDto userExpected = new UserDto(userId, "Deyky", "teste@teste.com", date);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.findById(userId);

        Assertions.assertEquals(userExpected, userDto);
    }

    @Test
    void shouldReturnUserNotFound_WhenFindByUserWithInvalidId() {
        Long userId = 1L;
        Date date = new Date();
        User user = new User(userId, "Deyky", "123456", "teste@teste.com", date);
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));

        UserNotFoundException error =
                Assertions.assertThrows(UserNotFoundException.class, () -> {
                    userService.findById(userId);
                });

        Assertions.assertTrue(error instanceof UserNotFoundException);
    }

    @Test
    void shouldDelete_WhenUseValidId() {
        Long userId = 1L;
        User user = new User(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(user);

        userService.delete(userId);

        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void shouldReturnUserNotFound_WhenUseInvalidId() {
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));

        UserNotFoundException error =
                Assertions.assertThrows(UserNotFoundException.class, () -> {
                    userService.delete(userId);
                });

        Assertions.assertTrue(error instanceof UserNotFoundException);
    }


    @Test
    void shouldReturnUserDto_WhenUpdateCorrectly() {
        Long userId = 1L;
        Date date = new Date();
        User user = new User(userId, "Deyky", "123456", "deyky@teste.com", date);
        UserUpdateDto userUpdateDto = new UserUpdateDto("janbrolhando", "janbrolhando@teste.com", date);
        UserDto userExpected = new UserDto(userId, "janbrolhando", "janbrolhando@teste.com", date);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userResponseDto = userService.update(userId, userUpdateDto);

        Assertions.assertEquals(userExpected, userResponseDto);
    }

    @Test
    void shouldReturnUserError_WhenUpdateWithInvalidId() {
        Long userId = 1L;
        Date date = new Date();
        UserUpdateDto userUpdateDto = new UserUpdateDto("janbrolhando", "janbrolhando@teste.com", date);
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));

        UserNotFoundException error =
                Assertions.assertThrows(UserNotFoundException.class, () -> {
                    userService.update(userId, userUpdateDto);
                });

        Assertions.assertTrue(error instanceof UserNotFoundException);
    }

}