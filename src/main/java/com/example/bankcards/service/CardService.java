package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.StatusCard;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardException;
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
    public CardDto createCard(CardDto cardDTO) {
        Card card = cardMapper.cardDtoToCard(cardDTO);

        card.setNumber(cardNumberService.generateNextCardNumber());
        card.setExpDate(LocalDateTime.now().plusYears(5));

        cardRepository.save(card);
        return cardMapper.cardToCardDto(card);
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

        if (page == null) {
            return cardMapper.cardListToCardDtoList(cardRepository.findAllByUserId(idUser));
        } else {
            return cardMapper.cardListToCardDtoList(cardRepository
                    .findAllByUserId(idUser, PageRequest.of(--page, 10, Sort.by("id"))).getContent());
        }
    }



    public CardDto findOne(int id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException(String.valueOf(id)));
        return cardMapper.cardToCardDto(cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException(String.valueOf(id))));
    }


    @Transactional
    public CardDto updateCard(Integer id, CardDto cardDto) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException(String.valueOf(id)));

        cardMapper.updateCardFromDto(cardDto, card);
        cardRepository.save(card);

        return cardMapper.cardToCardDto(card);
    }
}