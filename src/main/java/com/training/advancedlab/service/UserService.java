package com.training.advancedlab.service;

import com.training.advancedlab.exception.UserNotFoundException;
import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.entity.User;
import com.training.advancedlab.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import static com.training.advancedlab.utils.DtoEntityConverter.convertToDto;
import static com.training.advancedlab.utils.DtoEntityConverter.convertToEntity;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
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

    public String createUser(@RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);

        return "ID ASSIGNED TO THE USER: " + savedUser.getId();
    }

    public void updateUser(Long id, @RequestBody UserDto userDto) {
        userRepository.findById(id)
                .map(userEntity -> updateOrCreateUser(userDto, userEntity)).orElseGet(() -> {
                    User userEntity = new User();
                    userDto.setId(id);
                    return updateOrCreateUser(userDto, userEntity);
        });
    }

    public void deleteUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }


    private User updateOrCreateUser(@RequestBody UserDto userDto, User userEntity) {
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setBio(userDto.getBio());
        return userRepository.save(userEntity);
    }
}
