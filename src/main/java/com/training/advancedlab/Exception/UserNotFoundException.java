package com.training.advancedlab.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Could not find user with the id: " + id);
    }
}
