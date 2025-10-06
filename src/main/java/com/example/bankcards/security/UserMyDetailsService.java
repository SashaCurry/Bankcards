package com.example.bankcards.security;

import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMyDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserMyDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);

        return user.map(UserMyDetails::new)
                   .orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found"));
    }
}
