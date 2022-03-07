INSERT INTO role(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

-- https://es.wikipedia.org/wiki/ISO_4217
INSERT INTO currency(description, name, code)
VALUES ('Sol (S/)', 'Soles peruanos', 'PEN'),
       ('Dólar ($)', 'Dólar estadounidense', 'USD'),
       ('Euro (€)', 'Euros españoles', 'EUR'),
       ('Yen (¥)', 'Yen japonés', 'JPY'),
       ('Peso mexicano ($)', 'Peso mexicano', 'MXP');

INSERT INTO exchange_rate (sorce_currency_id, destination_currency_id, "value", creation_date)
VALUES (1, 2, 0.265, now()),
       (2, 1, 3.770, now()),
       (1, 3, 0.250, now()),
       (3, 1, 4.000, now()),
       (2, 3, 0.920, now()),
       (3, 2, 1.090, now());