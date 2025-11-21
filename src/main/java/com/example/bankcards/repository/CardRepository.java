package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findAllByUserId(User user);

    Page<Card> findAllByUserId(User user, Pageable pageable);

    @Query("SELECT c FROM Card c WHERE " +
            "(LOWER(c.number) LIKE LOWER(CONCAT('%', :number1, '%'))) AND " +
            "(LOWER(c.number) LIKE LOWER(CONCAT('%', :number2, '%'))) AND " +
            "(LOWER(c.number) LIKE LOWER(CONCAT('%', :number3, '%')))" )
    List<Card> findAllByNumber(@Param("number1") String number1,
                               @Param("number2") String number2,
                               @Param("number3") String number3 );

    boolean existsByNumber(String cardNumber);
}
