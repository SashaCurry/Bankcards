--liquibase formatted sql

--changeset SashaCurry:create-admin-user
--comment: Создание администратора
INSERT INTO users(name, login, password, roles)
    VALUES ('Алексеев Александр Александрович', 'SashaCurry',
            '$2a$10$rGqmlYEbMU/7/qmZyRvgCONKuM9UwYicOCx3W6HcMOYhyJlJuodHG', 'ROLE_ADMIN:ROLE_USER');

--rollback DELETE FROM users WHERE login LIKE 'SashaCurry' AND name LIKE 'Алексеев Александр Александрович';