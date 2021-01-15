package com.training.advancedlab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void shouldNotAllowAccessToUnauthorizedUsers() throws Exception {
        mockMvc.perform(get("/user/")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAListOfAllTheUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto("Dan", "Friend"));
        users.add(new UserDto("Claude", "Video Game Character"));
        users.add(new UserDto("Elon", "CEO of Tesla"));
        ResponseEntity<List<UserDto>> returnedResponse = new ResponseEntity<>(users, HttpStatus.OK);
        when(userService.getUsers()).thenReturn(returnedResponse);

        String url = "/user/";
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(users);

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldFindExistingUser() throws Exception {
        UserDto newUser = new UserDto();
        newUser.setName("Benny");
        newUser.setBio("interesting kid");
        newUser.setId(1L);
        ResponseEntity<UserDto> returnedResponse = new ResponseEntity<>(newUser, HttpStatus.OK);

        when(userService.getUserById(1L)).thenReturn(returnedResponse);

        String createUrl = "/user/1";
        MvcResult mvcResult = mockMvc.perform(get(createUrl))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(newUser));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        UserDto newUser = new UserDto("Benny", "interesting kid");
        ResponseEntity<String> returnedResponse = new ResponseEntity<>("USER UPDATED SUCCESSFULLY", HttpStatus.OK);

        when(userService.updateUser(any(), any())).thenReturn(returnedResponse);

        String createUrl = "/user/1";
        MvcResult mvcResult = mockMvc.perform(put(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), "USER UPDATED SUCCESSFULLY");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUser() throws Exception {
        UserDto newUser = new UserDto("Benny", "interesting kid");
        ResponseEntity<String> returnedResponse = new ResponseEntity<>("ID ASSIGNED TO THE USER: 4", HttpStatus.CREATED);

        when(userService.createUser(any())).thenReturn(returnedResponse);

        String createUrl = "/user/";
        MvcResult mvcResult = mockMvc.perform(post(createUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(response.getContentAsString(), "ID ASSIGNED TO THE USER: 4");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateNewUser() throws Exception {
        ResponseEntity<String> returnedResponse = new ResponseEntity<>("USER DELETED SUCCESSFULLY", HttpStatus.ACCEPTED);

        when(userService.deleteUser(any())).thenReturn(returnedResponse);

        String createUrl = "/user/1";
        MvcResult mvcResult = mockMvc.perform(delete(createUrl))
                .andExpect(status().isAccepted())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        assertEquals(response.getContentAsString(), "USER DELETED SUCCESSFULLY");
    }
}