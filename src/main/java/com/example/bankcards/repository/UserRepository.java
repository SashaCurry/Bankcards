package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User u WHERE " +
           "(LOWER(u.name) LIKE LOWER(CONCAT('%', :name1, '%'))) AND " +
           "(LOWER(u.name) LIKE LOWER(CONCAT('%', :name2, '%'))) AND " +
           "(LOWER(u.name) LIKE LOWER(CONCAT('%', :name3, '%')))" )
    List<User> findAllByName(@Param("name1") String name1, @Param("name2") String name2, @Param("name3") String name3);

    boolean existsByLogin(String login);
}
