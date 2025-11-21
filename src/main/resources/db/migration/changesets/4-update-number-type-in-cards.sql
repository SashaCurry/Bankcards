--liquibase formatted sql

--changeset SashaCurry:update-number-type-in-cards
--comment: Изменение типа колонки "number" в таблицу cards
ALTER TABLE cards ALTER COLUMN number TYPE varchar(20);

--rollback ALTER TABLE cards ALTER COLUMN number TYPE int USING number::integer;