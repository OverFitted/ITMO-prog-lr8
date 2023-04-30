CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE unit_of_measure (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE color (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE country (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE location (
                          id SERIAL PRIMARY KEY,
                          x INT NOT NULL,
                          y BIGINT NOT NULL,
                          z BIGINT NOT NULL,
                          name VARCHAR(255)
);

CREATE TABLE person (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        height INT CHECK (height > 0),
                        eye_color_id INT NOT NULL REFERENCES color(id),
                        hair_color_id INT REFERENCES color(id),
                        nationality_id INT REFERENCES country(id),
                        location_id INT NOT NULL REFERENCES location(id)
);

CREATE TABLE coordinates (
                             id SERIAL PRIMARY KEY,
                             x DOUBLE PRECISION NOT NULL,
                             y REAL CHECK (y > -452) NOT NULL
);

CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         coordinates_id INT NOT NULL REFERENCES coordinates(id),
                         creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
                         price DOUBLE PRECISION CHECK (price > 0),
                         part_number VARCHAR(51) CHECK (LENGTH(part_number) >= 30),
                         manufacture_cost REAL NOT NULL,
                         unit_of_measure_id INT NOT NULL REFERENCES unit_of_measure(id),
                         owner_id INT REFERENCES person(id),
                         user_id INT REFERENCES users(id)
);

-- Заполните таблицы значениями из enum-классов
INSERT INTO unit_of_measure (name) VALUES ('KILOGRAMS'), ('METERS'), ('CENTIMETERS'), ('SQUARE_METERS'), ('MILLIGRAMS');
INSERT INTO color (name) VALUES ('GREEN'), ('BLUE'), ('ORANGE'), ('WHITE'), ('RED'), ('YELLOW');
INSERT INTO country (name) VALUES ('RUSSIA'), ('INDIA'), ('THAILAND'), ('NORTH_KOREA');

-- Админ аккаунт
INSERT INTO users (username, password) values ('admin', 'admin');
