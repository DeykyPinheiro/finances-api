CREATE TABLE account
(
    id                  serial                 PRIMARY KEY,
    user_id             bigint                 NOT NULL,
    bank                varchar(20)            NOT NULL,
    account_id          bigint                 NOT NULL,


    creation_date TIMESTAMP DEFAULT current_timestamp,
    date_update TIMESTAMP DEFAULT current_timestamp,

















    subtotal             numeric(20, 2) NOT NULL,
    taxa_frete           numeric(10, 2) NOT NULL,
    valor_total          numeric(10, 2) NOT NULL,
    data_criacao         timestamp      NOT NULL,
    data_confirmacao     timestamp,
    data_cancelamento    timestamp,
    data_entrega         timestamp,

    endereco_cep         varchar(20)    NOT NULL,
    endereco_logradouro  varchar(255)   NOT NULL,
    endereco_numero      varchar(20)    NOT NULL,
    endereco_complemento varchar(255),
    endereco_bairro      varchar(255)   NOT NULL,

    restaurante_id       bigint         NOT NULL,
    usuario_cliente_id   bigint         NOT NULL,
    forma_pagamento_id   bigint         NOT NULL,

    CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
    CONSTRAINT fk_pedido_usuario_cliente FOREIGN KEY (usuario_cliente_id) REFERENCES usuario (id),
    CONSTRAINT fk_pedido_forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
);
