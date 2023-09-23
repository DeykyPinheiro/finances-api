CREATE TABLE account
(
    id                  serial                 PRIMARY KEY,
    user_id             bigint                 NOT NULL,
    bank                varchar(20)            NOT NULL,
    creation_date       TIMESTAMP DEFAULT current_timestamp,
    date_update         TIMESTAMP DEFAULT current_timestamp,

    CONSTRAINT fk_account_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE transaction
(
    id                  serial                 PRIMARY KEY,
    account_id          bigint                 NOT NULL,
    transaction_type    varchar(500)           NOT NULL,
    amount              NUMERIC(10, 2)         NOT NULL,



    creation_date       TIMESTAMP DEFAULT current_timestamp,
    date_update         TIMESTAMP DEFAULT current_timestamp,

    CONSTRAINT fk_transaction_account_id FOREIGN KEY (account_id) REFERENCES account(id)
);
