package com.example.bankcards.service;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.IncorrectStatusExpection;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
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
    public void createCard(CardDTO cardDTO) {
        String curStatus = cardDTO.getStatus();
        if (!curStatus.equals("Active") && !curStatus.equals("Blocked") && !curStatus.equals("Expired")) {
            throw new IncorrectStatusExpection("Некорректный статус карты. Возможны следующие варианты: Active, Blocked, Expired!");
        }

        Card card = cardMapper.cardDTOToCard(cardDTO);

        card.setNumber(cardNumberService.generateNextCardNumber());
        card.setExpMonth((byte) LocalDate.now().getMonthValue());
        card.setExpYear(Year.of(LocalDate.now().getYear() + 5));

        User user = userRepository.findById(cardDTO.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException("Пользователь с id = " + cardDTO.getUserId() + " не найден!"));
        card.setUserId(user);

        cardRepository.save(card);
    }


    @Transactional
    public void deleteCard(int id) {
        if (cardRepository.findById(id).isPresent()) {
            cardRepository.deleteById(id);
        } else {
            throw new CardNotFoundException("Карта с id = " + id + " не найдена!");
        }
    }


    public List<CardDTO> findAll() {
        return cardMapper.cardListToCardDTOList(cardRepository.findAll(Sort.by("id")));
    }

    public List<CardDTO> findAll(Integer page) {
        return cardMapper.cardListToCardDTOList(
                cardRepository.findAll(PageRequest.of(page, 10, Sort.by("id"))).getContent());
    }


//    public List<CardDTO> findAllByNumber(String number) {
//        String[] numberParts = number.split(" ");
//
//        List<Card> cardList;
//        switch (numberParts.length) {
//            case 1 -> cardList = cardRepository.findAllByNumber(numberParts[0], "", "");
//            case 2 -> cardList = cardRepository.findAllByNumber(numberParts[0], numberParts[1], "");
//            case 3 -> cardList = cardRepository.findAllByNumber(numberParts[0], numberParts[1], numberParts[2]);
//            default -> cardList = cardRepository.findAll();
//        };
//
//        return cardMapper.cardListToCardDTOList(cardList);
//    }


    public List<CardDTO> findAllByUser(Integer userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            return cardMapper.cardListToCardDTOList(cardRepository.findAllByUserId(user));
        } else {
            throw new UserNotFoundException("Пользователь с id = " + userId + " не найден!");
        }
    }

    public List<CardDTO> findAllByUser(Integer userId, Integer page) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            if (page == null) {
                return cardMapper.cardListToCardDTOList(cardRepository.findAllByUserId(user));
            } else {
                return cardMapper.cardListToCardDTOList(cardRepository
                        .findAllByUserId(user, PageRequest.of(page, 10, Sort.by("id"))).getContent());
            }
        } else {
            throw new UserNotFoundException("Пользователь с id = " + userId + " не найден!");
        }
    }



    public CardDTO findOne(int id) {
        if (cardRepository.findById(id).isPresent()) {
            return cardMapper.cardToCardDTO(cardRepository.findById(id).get());
        } else {
            throw new CardNotFoundException("Карта с id = " + id + " не найдена!");
        }
    }


    @Transactional
    public void updateCard(Integer id, CardDTO cardDTO) {
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

        if (cardDTO.getExpMonth() != null) {
            card.setExpMonth(cardDTO.getExpMonth());
        }
        if (cardDTO.getExpYear() != null) {
            card.setExpYear(cardDTO.getExpYear());
        }
        if (cardDTO.getStatus() != null) {
            String curStatus = cardDTO.getStatus();
            if (curStatus.equals("Active") || curStatus.equals("Blocked") || curStatus.equals("Expired")) {
                card.setStatus(cardDTO.getStatus());
            } else {
                throw new IncorrectStatusExpection(
                        "Некорректный статус карты. Возможны следующие варианты: Active, Blocked, Expired!");
            }
        }
        if (cardDTO.getBalance() != null) {
            card.setBalance(cardDTO.getBalance());
        }

        cardRepository.save(card);
    }
}