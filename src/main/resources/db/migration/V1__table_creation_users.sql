CREATE TABLE users (
    id serial PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    birth_date DATE,
    creation_date TIMESTAMP DEFAULT current_timestamp,
    date_update TIMESTAMP DEFAULT current_timestamp
);
