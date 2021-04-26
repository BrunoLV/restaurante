CREATE TABLE tb_produtos
(
    id            SERIAL,
    guid          VARCHAR(100)   NOT NULL UNIQUE,
    nome          VARCHAR(100)   NOT NULL UNIQUE,
    descricao     VARCHAR(200)   NOT NULL,
    data_cadastro DATE           NOT NULL,
    valor         DECIMAL(17, 2) NOT NULL,
    fabricante    VARCHAR(100)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE
    SEQUENCE seq_produtos AS BIGINT INCREMENT BY 1 START
    WITH 1 OWNED BY tb_produtos.id;