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
import org.springframework.beans.factory.annotation.Autowired;
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
        assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(objectMapper.writeValueAsString(expectedResponse), objectMapper.writeValueAsString(actualResponse));
    }

    @Test
    public void shouldGetSpecificUsers() throws JsonProcessingException {
        User user = new User();
        user.setId(1L);
        user.setBio("bio");
        user.setName("name");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = DtoEntityConverter.convertToDto(user);
        ResponseEntity<UserDto> expectedResponse = new ResponseEntity<>(userDto, HttpStatus.OK);
        ResponseEntity<UserDto> actualResponse = service.getUserById(1L);
        assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(objectMapper.writeValueAsString(expectedResponse), objectMapper.writeValueAsString(actualResponse));
    }

    @Test
    public void shouldCreateUser() throws JsonProcessingException {
        User user = new User();
        user.setId(1L);
        user.setBio("bio");
        user.setName("name");

        when(userRepository.save(user)).thenReturn(user);

        UserDto userDto = DtoEntityConverter.convertToDto(user);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("ID ASSIGNED TO THE USER: 1", HttpStatus.CREATED);
        ResponseEntity<String> actualResponse = service.createUser(userDto);
        assertEquals(actualResponse.getStatusCode(), HttpStatus.CREATED);
        assertEquals(objectMapper.writeValueAsString(expectedResponse), objectMapper.writeValueAsString(actualResponse));
    }

}