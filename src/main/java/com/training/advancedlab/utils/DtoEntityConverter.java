package com.training.advancedlab.utils;

import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DtoEntityConverter {

    public static UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setBio(user.getBio());
        return dto;
    }

    public static List<UserDto> convertToDto(List<User> users) {
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

    public static User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setBio(userDto.getBio());
        return user;
    }

    public static List<User> convertToEntity(List<UserDto> users) {
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
