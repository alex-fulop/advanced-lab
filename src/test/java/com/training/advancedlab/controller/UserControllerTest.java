package com.training.advancedlab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ObjectMapper.class})
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @BeforeEach
    private void setup() {
    }

    @Test
    void shouldReturnAListOfAllTheUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto(1L, "Dan", "Friend"));
        users.add(new UserDto(2L, "Claude", "Video Game Character"));
        users.add(new UserDto(3L, "Elon", "CEO of Tesla"));

        when(userService.getUsers()).thenReturn(users);

        String url = "/user/list";
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(users);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    void shouldFindTheUserAfterCreatingIt() throws Exception {
        UserDto newUser = new UserDto(4L, "Frank", "Too OP");

        when(userService.createUser(newUser)).thenReturn(String.valueOf(newUser.getId()));

        String createUrl = "/user/create";
        mockMvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("4"));
    }
}