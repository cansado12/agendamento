CREATE TABLE clientes (
                          id         BIGINT       NOT NULL AUTO_INCREMENT,
                          nome       VARCHAR(150) NOT NULL,
                          telefone   VARCHAR(11)  NOT NULL,
                          created_at DATETIME     NOT NULL,

                          CONSTRAINT pk_clientes PRIMARY KEY (id)
);