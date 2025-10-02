package com.example.bankcards.service;

import com.example.bankcards.entity.User;
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

    public User findOne(User user) {
        return userRepository.findById(user.getId()).orElse(null);
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean userIsExists(User user) {
        return userRepository.existsByLogin(user.getLogin());
    }
}
