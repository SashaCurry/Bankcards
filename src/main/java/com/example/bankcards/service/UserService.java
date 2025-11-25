package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotCreatedException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.UserMapper;
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
            throw new UserNotFoundException(String.valueOf(id));
        }
    }

    public List<UserDto> findAll() {
        return userMapper.userListToUserDtoList(userRepository.findAll());
    }


    public List<UserDto> findAllByName(String name) {
        return userMapper.userListToUserDtoList(userRepository.findAllByNameIgnoreCase(name));
    }


    public UserDto findOne(int id) {
        if (userRepository.findById(id).isPresent()) {
            return userMapper.userToUserDto(userRepository.findById(id).get());
        } else {
            throw new UserNotFoundException(String.valueOf(id));
        }
    }


    @Transactional
    public void saveUser(UserDto userDTO) {
        User user = userMapper.userDtoToUser(userDTO);

        if (userIsExists(user.getLogin())) {
            throw new UserNotCreatedException(user.getLogin());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(UserDto userDTO) {
        if (userRepository.findById(userDTO.getId()).isPresent()) {
            User newUser = userRepository.findById(userDTO.getId()).get();

            if (!userDTO.getLogin().equals(newUser.getLogin()) && userIsExists(userDTO.getLogin())) {
                throw new UserNotCreatedException(userDTO.getLogin());
            }

            newUser.setName(userDTO.getName());
            newUser.setLogin(userDTO.getLogin());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRoles(userDTO.getRoles());
            newUser.setUpdatedAt(LocalDateTime.now());

            userRepository.save(newUser);
        } else {
            throw new UserNotFoundException(String.valueOf(userDTO.getId()));
        }
    }


    public boolean userIsExists(String login) {
        return userRepository.existsByLogin(login);
    }
}