CREATE TABLE servicos (
                          id           BIGINT         NOT NULL AUTO_INCREMENT,
                          nome_servico VARCHAR(150)   NOT NULL,
                          duracao      INT            NOT NULL,
                          preco        DECIMAL(10, 2) NOT NULL,
                          ativo        TINYINT(1)     NOT NULL DEFAULT 1,

                          CONSTRAINT pk_servicos PRIMARY KEY (id)
);