package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "id", ignore = true)
    Card cardDTOToCard(CardDTO cardDTO);

    CardDTO cardToCardDTO(Card card);

    List<CardDTO> cardListToCardDTOList(List<Card> cards);
}
