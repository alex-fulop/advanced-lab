package com.training.advancedlab.controller;

import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.repository.UserRepository;
import com.training.advancedlab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    private List<UserDto> result;

    @BeforeEach
    private void setup() {
        this.result = new ArrayList<>();
        result.add(new UserDto(1L, "Dan", "Friend"));
        result.add(new UserDto(2L, "Claude", "Video Game Character"));
        result.add(new UserDto(3L, "Elon", "CEO of Tesla"));
    }

    @Test
    void shouldReturnAListOfAllTheUsers() throws Exception {
        when(userService.getUsers()).thenReturn(result);

        this.mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()", is(result.size())));
    }
}