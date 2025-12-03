--liquibase formatted sql

--changeset SashaCurry:change-type-for-money
--comment: Изменение типа для хранение денег
ALTER TABLE cards ALTER COLUMN balance TYPE NUMERIC;

--rollback ALTER TABLE cards ALTER COLUMN balance TYPE REAL;