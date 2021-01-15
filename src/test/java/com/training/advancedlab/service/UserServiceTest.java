package com.training.advancedlab.service;

import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.entity.User;
import com.training.advancedlab.repository.UserRepository;
import com.training.advancedlab.utils.DtoEntityConverter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Dan", "billionaire"));
        users.add(new User("Kanye", "in paris"));
        users.add(new User("Mario", "where is he"));

//        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> dtos = DtoEntityConverter.convertToDto(users);
        ResponseEntity<List<UserDto>> expectedResponse = new ResponseEntity<>(dtos, HttpStatus.OK);
        ResponseEntity<List<UserDto>> actualResponse = service.getUsers();
        assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(expectedResponse, actualResponse);
    }
}