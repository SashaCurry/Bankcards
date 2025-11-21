package com.example.bankcards.service;

import com.example.bankcards.repository.CardRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardNumberService {
    @PersistenceContext
    private EntityManager entityManager;

    private final CardRepository cardRepository;

    private boolean sequenceInitialized = false;

    @Transactional
    public String generateNextCardNumber() {
        // Инициализируем последовательность при первом вызове
        if (!sequenceInitialized) {
            initializeSequence();
            sequenceInitialized = true;
        }

        int maxAttempts = 10;
        int attempt = 0;

        while (attempt < maxAttempts) {
            Long sequenceValue = ((Number) entityManager
                    .createNativeQuery("SELECT nextval('card_number_seq')")
                    .getSingleResult()).longValue();

            String cardNumber = formatCardNumber(sequenceValue);

            if (!cardRepository.existsByNumber(cardNumber)) {
                return cardNumber;
            }

            attempt++;
            System.out.println("Попытка " + attempt + ": номер " + cardNumber + " уже существует");
        }

        throw new IllegalStateException("Не удалось сгенерировать уникальный номер карты после " + maxAttempts + " попыток");
    }

    @Transactional
    protected void initializeSequence() {
        try {
            Long maxSequenceValue = getMaxSequenceValueFromCards();
            if (maxSequenceValue != null && maxSequenceValue > 0) {
                // Используем getSingleResult() вместо executeUpdate(), так как setval возвращает значение
                String updateSequenceQuery = "SELECT setval('card_number_seq', ?)";
                Object result = entityManager.createNativeQuery(updateSequenceQuery)
                        .setParameter(1, maxSequenceValue)
                        .getSingleResult();

                System.out.println("Последовательность инициализирована с значением: " + maxSequenceValue);
            }
        } catch (Exception e) {
            System.err.println("Ошибка инициализации последовательности: " + e.getMessage());
            // Не бросаем исключение дальше, чтобы не помечать транзакцию как rollback-only
        }
    }

    private Long getMaxSequenceValueFromCards() {
        try {
            String query = "SELECT MAX(CAST(REPLACE(REPLACE(REPLACE(number, ' ', ''), '7200', ''), '56', '') AS BIGINT)) " +
                    "FROM cards WHERE number LIKE '7200 56%'";
            Object result = entityManager.createNativeQuery(query).getSingleResult();
            return result != null ? ((Number) result).longValue() : null;
        } catch (Exception e) {
            System.err.println("Ошибка при получении максимального значения: " + e.getMessage());
            return null;
        }
    }

    private String formatCardNumber(Long sequenceValue) {
        String sequentialPart = String.format("%010d", sequenceValue);
        String part1 = sequentialPart.substring(0, 2);
        String part2 = sequentialPart.substring(2, 6);
        String part3 = sequentialPart.substring(6, 10);

        return String.format("7200 56%s %s %s", part1, part2, part3);
    }
}