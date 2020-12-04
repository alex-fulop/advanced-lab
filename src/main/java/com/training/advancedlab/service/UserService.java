package com.training.advancedlab.service;

import com.training.advancedlab.Exception.UserNotFoundException;
import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.entity.User;
import com.training.advancedlab.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return convertToDto(users);
    }

    public UserDto getUserById(@PathVariable("id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return convertToDto(user);
    }

    public void createUser(@RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        userRepository.save(user);
    }

    public void updateUser(@RequestBody UserDto userDto) {
        userRepository.findById(userDto.getId())
                .map(userEntity -> {
                    userEntity.setName(userDto.getName());
                    userEntity.setBio(userDto.getBio());
                    return userRepository.save(userEntity);
                }).orElseThrow(() -> new UserNotFoundException(userDto.getId()));
    }

    public void deleteUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setBio(user.getBio());
        return dto;
    }

    private List<UserDto> convertToDto(List<User> users) {
        List<UserDto> foundUsers = new ArrayList<>();
        users
                .forEach(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setBio(user.getBio());
                    foundUsers.add(dto);
                });

        return foundUsers;
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setBio(userDto.getBio());
        return user;
    }

    private List<User> convertToEntity(List<UserDto> users) {
        List<User> savedUsers = new ArrayList<>();
        users
                .forEach(userEntity -> {
                    User user = new User();
                    user.setId(userEntity.getId());
                    user.setName(userEntity.getName());
                    user.setBio(userEntity.getBio());
                    savedUsers.add(user);
                });

        return savedUsers;
    }
}
