package com.training.advancedlab.controller;

import com.training.advancedlab.dto.UserDto;
import com.training.advancedlab.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @ResponseBody
    @ApiOperation(value = "Create a new user",
            notes = "Provide a name and a description to create a new user")
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "Find a specific user by Id",
            notes = "Provide an Id to look for a specific user")
    public ResponseEntity<UserDto> getUserById(@ApiParam(value = "Id value value for the user you wish to find", required = true)
                               @PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    @ApiOperation(value = "Find all users",
            notes = "Return a list of all available users")
    public ResponseEntity<String> createUser(@ApiParam(value = "The payload containing the information for the new user", required = true)
                             @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a user's data",
            notes = "Provide an Id and a payload containing the values of the fields you wish to update")
    public ResponseEntity<String> updateUser(@ApiParam(value = "Id value value for the user you wish to update", required = true)
                           @PathVariable("id") Long id,
                           @ApiParam(value = "The payload containing the information for updating the user", required = true)
                           @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a user",
            notes = "Permanently removes a user and all its information")
    public ResponseEntity<String> deleteUser(@ApiParam(value = "Id value value for the user you wish to delete", required = true)
                           @PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
