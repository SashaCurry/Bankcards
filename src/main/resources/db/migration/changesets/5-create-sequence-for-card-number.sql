--liquibase formatted sql

--changeset SashaCurry:create-sequence-for-card-number
--comment: Добавление последовательности для автоматической генерации номера карты
CREATE SEQUENCE card_number_seq START 5679000000000000 INCREMENT 1;

--rollback DROP SEQUENCE card_number_seq;