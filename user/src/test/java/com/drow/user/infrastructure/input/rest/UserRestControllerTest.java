package com.drow.user.infrastructure.input.rest;

import com.drow.user.application.dto.request.UserRequestDto;
import com.drow.user.application.handler.IUserHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserHandler userHandler;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .documentoDeIdentidad(12568)
                .nombre("Juan")
                .apellido("Pérez")
                .celular("+1235678")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .correo("juan.perez@example.com")
                .clave("password123")
                .build();
    }

    @Test
    void userController_SaveUser_ReturnStatusCreated() throws Exception {
        String userRequestDtoJson = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestDtoJson));

        resultActions.andExpect(status().isCreated());
    }
}