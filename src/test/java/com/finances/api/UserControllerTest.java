package com.finances.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finances.api.controller.UserController;
import com.finances.domain.dto.user.UserDto;
import com.finances.domain.dto.user.UserListDto;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.dto.user.UserUpdateDto;
import com.finances.domain.exception.UserNotFoundException;
import com.finances.domain.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;


import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String BASE_URL = "/users";

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnStatus200_WhenListAllUsers() throws Exception {
        Page<UserListDto> pagedResponse = new PageImpl<>(List.of(new UserListDto(1L, "Deyky", "deyky@test.com", new Date())));
        Mockito.when(userService.list(ArgumentMatchers.any())).thenReturn(pagedResponse);

        var response = mockMvc.perform(get(BASE_URL))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldReturnStatus200AndValidyBody_WhenListAllUsers() throws Exception {
        UserListDto user = new UserListDto(1L, "Deyky", "deyky@test.com", new Date());
        Page<UserListDto> pagedResponse = new PageImpl<>(List.of(user));
        Mockito.when(userService.list(ArgumentMatchers.any())).thenReturn(pagedResponse);

        ResultActions resultActions = mockMvc.perform(get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(jsonResponse.equals(objectMapper.writeValueAsString(pagedResponse)));
    }

    //    @WithMockUser can i use for autentication
    @Test
    void shouldReturnStatus200_WhenQueryUserValid() throws Exception {
        UserDto userDto = new UserDto(1L, "Deyky", "deyky@teste.com", new Date());
        Mockito.when(userService.findById(1l)).thenReturn(userDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{userId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(userDto), jsonResponse);
    }

    @Test
    void shouldReturnStatus201_WhenCreatedUserValid() throws Exception {
        Date date = new Date();
        UserSaveDto userSavedto = new UserSaveDto("Deyky", "123456", "teste@teste.com", date);
        UserDto userExpected = new UserDto(1L, "Deyky", "deyky@teste.com", date);
        Mockito.when(userService.save(userSavedto)).thenReturn(userExpected);

        ResultActions resultActions = mockMvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userSavedto)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();


        Assertions.assertEquals(HttpStatus.CREATED.value(), resultActions.andReturn().getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(userExpected), jsonResponse);
    }

    //
    @Test
    void shouldReturnStatus400_WhenCreateUserWithoutPassword() throws Exception {
        Date date = new Date();
        UserSaveDto userSavedto = new UserSaveDto(null, "", "teste@test.com", date);


        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSavedto)));


        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    void shouldReturnStatus400_WhenCreateUserWithoutName() throws Exception {
        Date date = new Date();
        UserSaveDto userSavedto = new UserSaveDto("", "123456", "teste@teste.com", date);

        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSavedto)));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    void shouldReturnStatus400_WhenCreateUserWithoutEmail() throws Exception {
        Date date = new Date();
        UserSaveDto userSavedto = new UserSaveDto("Deyky", "123456", null, date);

        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSavedto)));


        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    void shouldReturnStatus400_WhenCreateUserWithoutBirtDate() throws Exception {
        UserSaveDto userSavedto = new UserSaveDto("Deyky", "123456", "teste@teste.com", null);

        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSavedto)));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    void shouldReturnStatusAndBodyCorrect_WhenCreateUserValid() throws Exception {
        Date date = new Date();
        UserSaveDto userSavedto = new UserSaveDto("Deyky", "123456", "teste@teste.com", date);
        UserDto expectedUser = new UserDto(1L, "Deyky", "teste@teste.com", date);
        Mockito.when(userService.save(userSavedto)).thenReturn(expectedUser);

        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSavedto)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.CREATED.value(), resultActions.andReturn().getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(expectedUser), jsonResponse);
    }

    @Test
    void shouldReturnUserNotFoundException_WhenQueryUserNonexistent() throws Exception {
        Long userId = 1L;
        Mockito.when(userService.findById(userId)).thenThrow(new UserNotFoundException(userId));

        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/{userId}", userId));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(String.format(String.format("Não existe um cadastro de usuario com código %d", userId)),
                resultActions.andReturn().getResolvedException().getMessage());
    }


    @Test
    void shouldReturnStatus200_WhenQueryUserExistent() throws Exception {
        Date date = new Date();
        Long userId = 1L;
        UserSaveDto userdto = new UserSaveDto("Deyky", "123456", "teste@teste.com", date);
        UserDto expectedUser = new UserDto(userId, "Deyky", "teste@teste.com", date);
        Mockito.when(userService.findById(userId)).thenReturn(expectedUser);

        ResultActions resultActions = mockMvc.perform(
                get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdto)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();


        Assertions.assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(expectedUser), jsonResponse);
    }

    @Test
    void shouldReturnStatus204_WhenDeleteUserValid() throws Exception {
        Long userId = 1L;
        Mockito.doNothing().when(userService).delete(userId);

        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/{userId}", userId));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    void shouldReturnStatus204_WhenDeleteUserNonexistent() throws Exception {
        Long userId = 1L;
        Mockito.doThrow(new UserNotFoundException(userId)).when(userService).delete(userId);

        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/{userId}", userId))
                .andExpect(status().isNotFound())
//                .andExpect(content().string(String.format("Não existe um cadastro de usuario com código %d", userId)));
                .andExpect(jsonPath("$.detail", is(String.format("Não existe um cadastro de usuario com código %d", userId))));
    }

    @Test
    void shouldReturnStatus200_WhenUpdateNameOfUserValid() throws Exception {
        Date date = new Date();
        Long userId = 1L;
        UserUpdateDto userUpdateDto = new UserUpdateDto("Deyky", null, null);
        UserDto userExpected = new UserDto(userId, "Deyky", "deyky@teste.com", date);
        Mockito.when(userService.update(userId, userUpdateDto)).thenReturn(userExpected);

        ResultActions resultActions = mockMvc.perform(
                put(BASE_URL + "/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(userExpected), jsonResponse);
    }

    @Test
    void shouldReturnStatus200_WhenUpdateEmailOfUserValid() throws Exception {
        Date date = new Date();
        Long userId = 1L;
        UserUpdateDto userUpdateDto = new UserUpdateDto(null, "teste@teste.com", null);
        UserDto userExpected = new UserDto(userId, "Deyky", "deyky@teste.com", date);
        Mockito.when(userService.update(userId, userUpdateDto)).thenReturn(userExpected);

        ResultActions resultActions = mockMvc.perform(
                put(BASE_URL + "/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(userExpected), jsonResponse);
    }

    @Test
    void shouldReturnStatus200_WhenUpdateBirthDateOfUserValid() throws Exception {
        Date date = new Date();
        Long userId = 1L;
        UserUpdateDto userUpdateDto = new UserUpdateDto(null, null, date);
        UserDto userExpected = new UserDto(userId, "Deyky", "deyky@teste.com", date);
        Mockito.when(userService.update(userId, userUpdateDto)).thenReturn(userExpected);

        ResultActions resultActions = mockMvc.perform(
                put(BASE_URL + "/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(userExpected), jsonResponse);
    }

    @Test
    void shouldReturnStatus404_WhenUpdateInvalidUser() throws Exception {
        Date date = new Date();
        Long userId = 1L;
        UserUpdateDto userUpdateDto = new UserUpdateDto(null, null, date);
        Mockito.when(userService.update(userId, userUpdateDto)).thenThrow(new UserNotFoundException(userId));

        ResultActions resultActions = mockMvc.perform(
                        put(BASE_URL + "/{userId}", userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(jsonPath("$.detail", is(String.format("Não existe um cadastro de usuario com código %d", userId))));
    }
}