package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDTO {
    private Integer id;

    @NotNull(message = "Параметр name не может быть пустым")
    @NotBlank(message = "Параметр name не может состоять только из пробелов")
    private String name;

    @NotNull(message = "Параметр login не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String login;

    @NotNull(message = "Параметр password не может быть пустым")
    private String password;

    @NotNull(message = "Параметр roles не может быть пустым")
    private String roles;
}