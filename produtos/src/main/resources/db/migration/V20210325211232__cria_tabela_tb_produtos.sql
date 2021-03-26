CREATE TABLE tb_produtos
(
    id            BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    guid          VARCHAR(255)   NOT NULL UNIQUE,
    nome          VARCHAR(100)   NOT NULL UNIQUE,
    descricao     TEXT           NOT NULL,
    data_cadastro DATE           NOT NULL,
    valor         DECIMAL(17, 2) NOT NULL,
    fabricante    VARCHAR(100)   NOT NULL
)