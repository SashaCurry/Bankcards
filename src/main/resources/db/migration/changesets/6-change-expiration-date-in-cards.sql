--liquibase formatted sql

--changeset SashaCurry:change-expiration-date-in-cards
--comment: До этого срок годности хранился в 2-х столбцах (exp_month и exp_year), теперь будет в одном
ALTER TABLE cards ADD COLUMN exp_date TIMESTAMP;

UPDATE cards SET exp_date = MAKE_TIMESTAMP(exp_year, exp_month, 1, 0, 0, 0);

UPDATE cards SET exp_date = (DATE_TRUNC('MONTH', exp_date) + INTERVAL '1 MONTH - 1 day')::TIMESTAMP WHERE exp_date IS NOT NULL;

ALTER TABLE cards ALTER COLUMN exp_date SET NOT NULL;

ALTER TABLE cards DROP COLUMN exp_month;
ALTER TABLE cards DROP COLUMN exp_year;

--rollback ALTER TABLE cards ADD COLUMN exp_year INTEGER;
--rollback ALTER TABLE cards ADD COLUMN exp_month INTEGER;

--rollback UPDATE cards
--rollback SET exp_year = EXTRACT(YEAR FROM expiration_date),
--rollback exp_month = EXTRACT(MONTH FROM expiration_date);

--rollback ALTER TABLE cards ALTER COLUMN exp_year SET NOT NULL;
--rollback ALTER TABLE cards ALTER COLUMN exp_month SET NOT NULL;

--rollback ALTER TABLE cards DROP COLUMN expiration_date;