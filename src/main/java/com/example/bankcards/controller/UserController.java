package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDto;
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
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.findAllByName(name), HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.findOne(id), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> registerUser(@RequestBody @Valid UserDto userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") int id, @RequestBody UserDto userDTO) {
        userDTO.setId(id);
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}