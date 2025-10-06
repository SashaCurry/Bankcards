package com.example.bankcards.util;

import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.userIsExists(user)) {
            errors.rejectValue("login", "",
                    "Пользователь с логином " + user.getLogin() + " уже существует!");
        }
    }
}
