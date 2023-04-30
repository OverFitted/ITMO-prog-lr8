CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL
);

CREATE TABLE unit_of_measure
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE color
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE location
(
    id   SERIAL PRIMARY KEY,
    x    INT    NOT NULL,
    y    BIGINT NOT NULL,
    z    BIGINT NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE person
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    height         INT CHECK (height > 0),
    eye_color_id   INT          NOT NULL REFERENCES color (id),
    hair_color_id  INT REFERENCES color (id),
    nationality_id INT REFERENCES country (id),
    location_id    INT          NOT NULL REFERENCES location (id)
);

CREATE TABLE coordinates
(
    id SERIAL PRIMARY KEY,
    x  DOUBLE PRECISION      NOT NULL,
    y  REAL CHECK (y > -452) NOT NULL
);

CREATE TABLE product
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    coordinates_id     INT          NOT NULL REFERENCES coordinates (id),
    creation_date      DATE         NOT NULL DEFAULT CURRENT_DATE,
    price              DOUBLE PRECISION CHECK (price > 0),
    part_number        VARCHAR(51) CHECK (LENGTH(part_number) >= 30),
    manufacture_cost   REAL         NOT NULL,
    unit_of_measure_id INT          NOT NULL REFERENCES unit_of_measure (id),
    owner_id           INT REFERENCES person (id),
    user_id            INT REFERENCES users (id)
);

-- enum
INSERT INTO unit_of_measure (name)
VALUES ('KILOGRAMS'),
       ('METERS'),
       ('CENTIMETERS'),
       ('SQUARE_METERS'),
       ('MILLIGRAMS');
INSERT INTO color (name)
VALUES ('GREEN'),
       ('BLUE'),
       ('ORANGE'),
       ('WHITE'),
       ('RED'),
       ('YELLOW');
INSERT INTO country (name)
VALUES ('RUSSIA'),
       ('INDIA'),
       ('THAILAND'),
       ('NORTH_KOREA');

-- Админ аккаунт
INSERT INTO users (username, password)
values ('admin', 'admin');

-- Тестовые данные
INSERT INTO location (x, y, z, name)
VALUES (10, 20, 30, 'Location1'),
       (25, 35, 45, 'Location2'),
       (15, 40, 60, 'Location3');

INSERT INTO person (name, height, eye_color_id, hair_color_id, nationality_id, location_id)
VALUES ('John Doe', 180, 1, 2, 1, 1),
       ('Alice Smith', 165, 3, 4, 2, 2),
       ('Bob Johnson', 190, 5, 6, 3, 3);

INSERT INTO coordinates (x, y)
VALUES (12.34, 56.78),
       (23.45, 67.89),
       (34.56, 78.90);

INSERT INTO product (name, coordinates_id, price, part_number, manufacture_cost, unit_of_measure_id, owner_id, user_id)
VALUES ('Product1', 1, 100.00, '123456789012345678901234567890', 50.00, 1, 1, 1),
       ('Product2', 2, 200.00, '234567890123456789012345678901', 100.00, 2, 2, 1),
       ('Product3', 3, 300.00, '345678901234567890123456789012', 150.00, 3, 3, 1);
