package com.training.advancedlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.entity.User;
import com.training.advancedlab.repository.UserRepository;
import com.training.advancedlab.utils.DtoEntityConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetAllUsers() throws JsonProcessingException {
        List<User> users = new ArrayList<>();
        users.add(new User("Dan", "billionaire"));
        users.add(new User("Kanye", "in paris"));
        users.add(new User("Mario", "where is he"));

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> dtos = DtoEntityConverter.convertToDto(users);
        ResponseEntity<List<UserDto>> expectedResponse = new ResponseEntity<>(dtos, HttpStatus.OK);
        ResponseEntity<List<UserDto>> actualResponse = service.getUsers();
        assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(expectedResponse.getBody()), objectMapper.writeValueAsString(actualResponse.getBody()));
    }

    @Test
    public void shouldGetSpecificUsers() throws JsonProcessingException {
        User user = getUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = DtoEntityConverter.convertToDto(user);
        ResponseEntity<UserDto> expectedResponse = new ResponseEntity<>(userDto, HttpStatus.OK);
        ResponseEntity<UserDto> actualResponse = service.getUserById(1L);
        assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(expectedResponse.getBody()), objectMapper.writeValueAsString(actualResponse.getBody()));
    }

    @Test
    public void shouldCreateUser() throws JsonProcessingException {
        User user = getUser();

        UserDto userDto = DtoEntityConverter.convertToDto(user);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("NEW USER CREATE SUCCESSFULLY", HttpStatus.CREATED);
        ResponseEntity<String> actualResponse = service.createUser(userDto);
        assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(expectedResponse.getBody()), objectMapper.writeValueAsString(actualResponse.getBody()));
    }

    @Test
    public void shouldUpdateUser() throws JsonProcessingException {
        User user = getUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserDto userDto = DtoEntityConverter.convertToDto(user);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("USER UPDATED SUCCESSFULLY", HttpStatus.OK);
        ResponseEntity<String> actualResponse = service.updateUser(1L, userDto);
        assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(expectedResponse.getBody()), objectMapper.writeValueAsString(actualResponse.getBody()));
    }

    @Test
    public void shouldDeleteUser() throws JsonProcessingException {
        User user = getUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("USER DELETED SUCCESSFULLY", HttpStatus.ACCEPTED);
        ResponseEntity<String> actualResponse = service.deleteUser(1L);
        assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(expectedResponse.getBody()), objectMapper.writeValueAsString(actualResponse.getBody()));
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setBio("bio");
        user.setName("name");
        return user;
    }


}