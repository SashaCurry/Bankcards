package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    List<Card> findAll() {
        return cardRepository.findAll();
    }

    List<Card> findAllByUser(User user) {
        return cardRepository.findAllByUserId(user).orElse(null);
    }

    Card findOneById(int id) {
        return cardRepository.findById(id).orElse(null);
    }
}
