package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.UserDTO;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.IncorrectStatusExpection;
import com.example.bankcards.exception.PageNotFoundException;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;


    @PostMapping()
    public ResponseEntity<HttpStatus> createCard(@RequestBody @Valid CardDTO cardDTO) {
        cardService.createCard(cardDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCard(@PathVariable("id") Integer id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<CardDTO>> getAllCards(@RequestParam(value = "page", required = false) Integer page) {
        if (page == null) {
            return new ResponseEntity<>(cardService.findAll(), HttpStatus.OK);
        } else if (page < 1) {
            throw new IncorrectStatusExpection("Страница не может быть меньше 1!");
        } else {
            List<CardDTO> cards = cardService.findAll(--page);

            if (cards.isEmpty() && !cardService.findAll().isEmpty()) {
                throw new PageNotFoundException("Страницы с номером = " + page + " не существует!");
            }

            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
    }


    @GetMapping("/user")
    public ResponseEntity<List<CardDTO>> getAllCardsByUser(@RequestParam("idUser") Integer idUser,
                                                           @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) {
            return new ResponseEntity<>(cardService.findAllByUser(idUser), HttpStatus.OK);
        } else if (page < 1) {
            throw new IncorrectStatusExpection("Страница не может быть меньше 1!");
        } else {
            List<CardDTO> cards = cardService.findAllByUser(idUser, --page);

            if (cards.isEmpty() && !cardService.findAllByUser(idUser).isEmpty()) {
                throw new PageNotFoundException("Страницы с номером = " + page + " не существует!");
            }

            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getOneCard(@PathVariable("id") int id) {
        CardDTO cardDTO = cardService.findOne(id);

        if (cardDTO == null) {
            throw new CardNotFoundException("Карта с id = " + id + " не существует!");
        }

        return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCard(@PathVariable("id") int id, @RequestBody CardDTO cardDTO) {
        cardService.updateCard(id, cardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
