package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.NumberCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "expDate", ignore = true)
    Card cardDtoToCard(CardDto cardDTO);

    @Mapping(target = "userId", source = "userId.id")
    CardDto cardToCardDto(Card card);

    List<CardDto> cardListToCardDtoList(List<Card> cards);
}