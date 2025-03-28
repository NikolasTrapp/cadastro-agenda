CREATE TABLE IF NOT EXISTS hospedes
(
    id              UUID                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    nome            VARCHAR                     NOT NULL,
    documento       VARCHAR(14)                 NOT NULL,
    telefone        VARCHAR(19)                 NOT NULL,
    data_nascimento date,
    CONSTRAINT pk_hospedes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS quartos
(
    id            UUID                        NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    numero_quarto BIGINT                      NOT NULL,
    tipo_quarto   VARCHAR                     NOT NULL,
    CONSTRAINT pk_quartos PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reservas
(
    id                       UUID                        NOT NULL,
    created_at               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_entrada             date                        NOT NULL,
    data_saida               date                        NOT NULL,
    check_in                 TIMESTAMP WITHOUT TIME ZONE,
    check_out                TIMESTAMP WITHOUT TIME ZONE,
    numero_pessoas           SMALLINT                    NOT NULL,
    necessita_estacionamento BOOLEAN                     NOT NULL,
    valor                    NUMERIC(19, 4)              NOT NULL,
    valor_multa              NUMERIC(19, 4),
    hospede_id               UUID                        NOT NULL,
    quarto_id                UUID                        NOT NULL,
    CONSTRAINT pk_reservas PRIMARY KEY (id)
);

ALTER TABLE hospedes
    ADD CONSTRAINT uc_hospedes_documento UNIQUE (documento);

ALTER TABLE hospedes
    ADD CONSTRAINT uc_hospedes_telefone UNIQUE (telefone);

ALTER TABLE quartos
    ADD CONSTRAINT uc_quartos_numero_quarto UNIQUE (numero_quarto);

ALTER TABLE reservas
    ADD CONSTRAINT FK_RESERVAS_ON_HOSPEDE FOREIGN KEY (hospede_id) REFERENCES hospedes (id);

ALTER TABLE reservas
    ADD CONSTRAINT FK_RESERVAS_ON_QUARTO FOREIGN KEY (quarto_id) REFERENCES quartos (id);