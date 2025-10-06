package com.example.bankcards.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDTO {
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String roles;
}