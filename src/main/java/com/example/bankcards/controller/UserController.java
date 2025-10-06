package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotCreatedException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.ErrorResponse;
import com.example.bankcards.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;


    @DeleteMapping({"/{id}"})
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return new ResponseEntity<>(userMapper.userListToUserDTOList(userService.findAll()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userMapper.userListToUserDTOList(userService.findAllByName(name)), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(userMapper.userToUserDTO(userService.findOne(id)), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        User user = userMapper.userDTOToUser(userDTO);

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");

            throw new UserNotCreatedException(String.valueOf(errorMsg));
        }

        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setId(id);

        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}