package com.training.advancedlab.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ApiModel("User - the core object of the API ")
public class UserDto {
    @ApiModelProperty("The unique id of the user")
    private Long id;
    @ApiModelProperty("The name of the user")
    private String name;
    @ApiModelProperty("The description of the user")
    private String bio;

    public UserDto() {
    }

    public UserDto(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }
}
