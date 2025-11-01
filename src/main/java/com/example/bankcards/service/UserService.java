package com.example.bankcards.service;

import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotCreatedException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Transactional
    public void deleteUser(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Пользователь с id = " + id + " не найден!");
        }
    }

    public List<UserDTO> findAll() {
        return userMapper.userListToUserDTOList(userRepository.findAll());
    }


    public List<UserDTO> findAllByName(String name) {
        String[] nameParts = name.split(" ");

        List<User> userList;
        switch (nameParts.length) {
            case 1 -> userList = userRepository.findAllByName(nameParts[0], "", "");
            case 2 -> userList = userRepository.findAllByName(nameParts[0], nameParts[1], "");
            case 3 -> userList = userRepository.findAllByName(nameParts[0], nameParts[1], nameParts[2]);
            default -> userList = userRepository.findAll();
        };

        return userMapper.userListToUserDTOList(userList);
    }


    public UserDTO findOne(int id) {
        if (userRepository.findById(id).isPresent()) {
            return userMapper.userToUserDTO(userRepository.findById(id).get());
        } else {
            throw new UserNotFoundException("Пользователь с id = " + id + " не найден!");
        }
    }


    @Transactional
    public void saveUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        if (userIsExists(user.getLogin())) {
            throw new UserNotCreatedException("Пользователь с логином " + user.getLogin() + " уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(UserDTO userDTO) {
        if (userRepository.findById(userDTO.getId()).isPresent()) {
            User newUser = userRepository.findById(userDTO.getId()).get();

            if (!userDTO.getLogin().equals(newUser.getLogin()) && userIsExists(userDTO.getLogin())) {
                throw new UserNotCreatedException("Пользователь с логином = " + userDTO.getLogin() + " уже существует");
            }

            newUser.setName(userDTO.getName());
            newUser.setLogin(userDTO.getLogin());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRoles(userDTO.getRoles());
            newUser.setUpdatedAt(LocalDateTime.now());

            userRepository.save(newUser);
        } else {
            throw new UserNotFoundException("Пользователь с id = " + userDTO.getId() + " не найден!");
        }
    }


    public boolean userIsExists(String login) {
        return userRepository.existsByLogin(login);
    }
}