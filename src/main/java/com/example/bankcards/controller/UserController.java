package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @DeleteMapping({"/{id}"})
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.findAllByName(name), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneUser(@PathVariable("id") int id) {
        UserDTO userDTO = userService.findOne(id);

        if (userDTO == null) {
            throw new UserNotFoundException("Пользователь с id = " + id + " не существует!");
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> registerUser(@RequestBody @Valid UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}