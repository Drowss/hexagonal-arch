package com.drow.plazoleta.infrastructure.input.rest;

import com.drow.plazoleta.application.dto.request.RestaurantRequestDto;
import com.drow.plazoleta.application.handler.IRestaurantHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = RestaurantRestController.class)
class RestaurantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp() {
        restaurantRequestDto = RestaurantRequestDto.builder()
                .nit("1234")
                .nombre("Restaurant")
                .direccion("Calle 123")
                .telefono("1234567")
                .urlLogo("https://www.google.com")
                .idPropietario(1)
                .build();
    }

    @Test
    void restaurantController_SaveRestaurant_ReturnVoid() throws Exception {

        ResultActions response = mockMvc.perform(post("/api/v1/restaurant/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}