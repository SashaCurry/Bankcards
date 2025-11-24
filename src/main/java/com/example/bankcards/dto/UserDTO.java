package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDTO {
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    @Email
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String roles;
}