CREATE TABLE agendamentos (
                              id         BIGINT      NOT NULL AUTO_INCREMENT,
                              inicio     DATETIME    NOT NULL,
                              termino    DATETIME    NOT NULL,
                              status     VARCHAR(20) NOT NULL,
                              cliente_id BIGINT      NOT NULL,
                              servico_id BIGINT      NOT NULL,
                              created_at DATETIME    NOT NULL,
                              updated_at DATETIME    NOT NULL,

                              CONSTRAINT pk_agendamentos          PRIMARY KEY (id),
                              CONSTRAINT fk_agendamentos_cliente  FOREIGN KEY (cliente_id) REFERENCES clientes (id),
                              CONSTRAINT fk_agendamentos_servico  FOREIGN KEY (servico_id) REFERENCES servicos (id),
                              CONSTRAINT chk_agendamentos_status  CHECK (status IN ('AGENDADO', 'CANCELADO', 'COMPLETO')),
                              CONSTRAINT chk_agendamentos_horario CHECK (termino > inicio)
);

CREATE INDEX idx_agendamentos_inicio      ON agendamentos (inicio);
CREATE INDEX idx_agendamentos_cliente_id  ON agendamentos (cliente_id);