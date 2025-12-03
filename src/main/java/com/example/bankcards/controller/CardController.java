package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
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
    public ResponseEntity<CardDto> createCard(@RequestBody @Valid CardDto cardDTO) {
        return new ResponseEntity<>(cardService.createCard(cardDTO), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCard(@PathVariable("id") Integer id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<CardDto>> getAllCards(@RequestParam(value = "page", required = false) Integer page) {
        return new ResponseEntity<>(cardService.findAll(page), HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<List<CardDto>> getAllCardsByUser(@PathVariable("id") Integer idUser,
                                                           @RequestParam(value = "page", required = false) Integer page) {
        return new ResponseEntity<>(cardService.findAllByUser(idUser, page), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getOneCard(@PathVariable("id") int id) {
        return new ResponseEntity<>(cardService.findOne(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(@PathVariable("id") Integer id, @RequestBody CardDto cardDTO) {
        return new ResponseEntity<>(cardService.updateCard(id, cardDTO), HttpStatus.OK);
    }
}
