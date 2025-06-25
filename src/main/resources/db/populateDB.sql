DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

DELETE FROM meals;

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2025-06-15 12:00:00', 'Обед', 1000),
       (100000, '2025-06-15 9:00:00', 'Завтрак', 280),
       (100001,'2025-06-15 18:00:00', 'Ужин', 700);


