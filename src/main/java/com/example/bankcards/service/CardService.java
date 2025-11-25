package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.StatusCard;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;
    private final CardNumberService cardNumberService;


    @Transactional
    public void createCard(CardDto cardDTO) {
        Card card = cardMapper.cardDtoToCard(cardDTO);

        card.setNumber(cardNumberService.generateNextCardNumber());
        card.setExpDate(LocalDateTime.now().plusYears(5));

        User user = userRepository.findById(cardDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(cardDTO.getUserId())));
        card.setUserId(user);

        cardRepository.save(card);
    }


    @Transactional
    public void deleteCard(int id) {
        if (cardRepository.findById(id).isEmpty()) {
            throw new CardNotFoundException(String.valueOf(id));
        }

        cardRepository.deleteById(id);
    }


    public List<CardDto> findAll(Integer page) {
        if (page == null) {
            return cardMapper.cardListToCardDtoList(cardRepository.findAll());
        }

        return cardMapper.cardListToCardDtoList(
                cardRepository.findAll(PageRequest.of(--page, 10, Sort.by("id"))).getContent());
    }


    public List<CardDto> findAllByUser(Integer idUser, Integer page) {
        if (userRepository.findById(idUser).isEmpty()) {
            throw new UserNotFoundException(String.valueOf(idUser));
        }

        User user = userRepository.findById(idUser).get();
        if (page == null) {
            return cardMapper.cardListToCardDtoList(cardRepository.findAllByUserId(user));
        } else {
            return cardMapper.cardListToCardDtoList(cardRepository
                    .findAllByUserId(user, PageRequest.of(--page, 10, Sort.by("id"))).getContent());
        }
    }



    public CardDto findOne(int id) {
        if (cardRepository.findById(id).isEmpty()) {
            throw new CardNotFoundException(String.valueOf(id));
        }

        return cardMapper.cardToCardDto(cardRepository.findById(id).get());
    }


    @Transactional
    public void updateCard(Integer id, CardDto cardDTO) {
        cardDTO.setId(id);
        if (cardRepository.findById(id).isEmpty()) {
            throw new CardNotFoundException("Карта с id = " + id + " не существует!");
        }

        Card card;
        if (cardRepository.findById(cardDTO.getId()).isPresent()) {
            card = cardRepository.findById(cardDTO.getId()).get();
        } else {
            throw new CardNotFoundException("Карта с id = " + cardDTO.getId() + " не найдена!");
        }

        if (cardDTO.getExpDate() != null) {
            card.setExpDate(cardDTO.getExpDate());
        }
        if (cardDTO.getStatus() != null) {
            card.setStatus(StatusCard.valueOf(cardDTO.getStatus()));
        }
        if (cardDTO.getBalance() != null) {
            card.setBalance(cardDTO.getBalance());
        }

        cardRepository.save(card);
    }
}