package com.training.advancedlab.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String bio;

    public User() {
    }

    public User(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }
}
