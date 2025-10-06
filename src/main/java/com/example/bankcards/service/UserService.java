package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotFoundException;
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
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void deleteUser(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Пользователь с id = " + id + " не найден!");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public List<User> findAllByName(String name) {
        String[] nameParts = name.split(" ");

        return switch (nameParts.length) {
            case 1 -> userRepository.findAllByName(nameParts[0], "", "");
            case 2 -> userRepository.findAllByName(nameParts[0], nameParts[1], "");
            case 3 -> userRepository.findAllByName(nameParts[0], nameParts[1], nameParts[2]);
            default -> userRepository.findAll();
        };
    }


    public User findOne(int id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new UserNotFoundException("Пользователь с id = " + id + " не найден!");
        }
    }


    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            User newUser = userRepository.findById(user.getId()).get();

            if (user.getName() != null) {
                newUser.setName(user.getName());
            }
            if (user.getLogin() != null) {
                newUser.setLogin(user.getLogin());
            }
            if (user.getPassword() != null) {
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getRoles() != null) {
                newUser.setRoles(user.getRoles());
            }
            newUser.setUpdatedAt(LocalDateTime.now());

            userRepository.save(newUser);
        } else {
            throw new UserNotFoundException("Пользователь с id = " + user.getId() + " не найден!");
        }
    }


    public boolean userIsExists(User user) {
        return userRepository.existsByLogin(user.getLogin());
    }
}