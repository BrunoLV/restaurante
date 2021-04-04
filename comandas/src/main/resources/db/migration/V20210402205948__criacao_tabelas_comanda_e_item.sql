CREATE TABLE tb_comandas
(
    id          SERIAL,
    guid        VARCHAR(100) NOT NULL UNIQUE,
    data        DATE         NOT NULL,
    numero_mesa INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE
SEQUENCE seq_comandas AS BIGINT INCREMENT BY 1 START
WITH 1 OWNED BY tb_comandas.id;

CREATE TABLE tb_itens
(
    id             SERIAL,
    guid           VARCHAR(100)   NOT NULL UNIQUE,
    nome           VARCHAR(100)   NOT NULL,
    fabricante     VARCHAR(100)   NOT NULL,
    valor_unitario DECIMAL(17, 2) NOT NULL,
    quantidade     INT            NOT NULL,
    guid_produto   VARCHAR(100)   NOT NULL,
    id_comanda     BIGINT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_comanda) REFERENCES tb_comandas (id)
);

CREATE
SEQUENCE seq_itens AS BIGINT INCREMENT BY 1 START
WITH 1 OWNED BY tb_itens.id;