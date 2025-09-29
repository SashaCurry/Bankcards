--liquibase formatted sql

--changeset SashaCurry:add-admin-user
--comment: Добавление администратора в систему
INSERT INTO users(firstname, midname, lastname, login, password, roles)
    VALUES ('Александр', 'Александрович', 'Алексеев',
            'SashaCurry', 'zaq11qaz', 'ROLE_ADMIN:ROLE_USER')

--rollback DELETE FROM users WHERE login LIKE 'SashaCurry' AND password LIKE 'zaq11qaz';