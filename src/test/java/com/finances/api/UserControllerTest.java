package com.finances.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finances.domain.dto.user.UserSaveDto;
import com.finances.domain.model.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


//    @WithMockUser can i use for autentication
    @Test
    void mustReturnStatus200_WhenQueryUserValid() throws Exception {
        var response = mockMvc.perform(get("/users"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void mustReturnStatus201_WhenCreatedUserValid() throws Exception {
        Date date = new Date();
        ObjectMapper objectMapper = new ObjectMapper();
        UserSaveDto userdto = new UserSaveDto("Deyky", "123456", "teste@teste.com", date);

        var response = mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(       objectMapper.writeValueAsString(userdto)))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void mustReturnStatus400_WhenCreateUserWithoutPassword() throws Exception {
        Date date = new Date();
        UserSaveDto userdto = new UserSaveDto("Deyky", "", "teste@test.com", date);
        var response = mockMvc.perform(post("/users", userdto))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void mustReturnStatus400_WhenCreateUserWithoutName() throws Exception {
        Date date = new Date();
        UserSaveDto userdto = new UserSaveDto("", "123456", "teste@test.com", date);
        var response = mockMvc.perform(post("/users", userdto))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void mustReturnStatus400_WhenCreateUserWithoutEmail() throws Exception {
        Date date = new Date();
        UserSaveDto userdto = new UserSaveDto("Deyky", "123456", "", date);
        var response = mockMvc.perform(post("/users", userdto))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void mustReturnStatus400_WhenCreateUserWithoutBirtDate() throws Exception {
        UserSaveDto userdto = new UserSaveDto("Deyky", "123456", "", null);
        var response = mockMvc.perform(post("/users", userdto))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


}