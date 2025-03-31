INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES ('e420eb03-96f2-4c10-9720-c43a0aedff95', 101, now(), now(), 'INDIVIDUAL');
INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES ('91388db7-2021-40d2-abf3-5a30876dcbe1', 202, now(), now(), 'DUPLO');
INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES ('5134c62e-60c4-4b07-9f03-bcc3d09f4b80', 303, now(), now(), 'TRIPLO');
INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES ('424e8ea7-d709-4549-ab4b-0aa5a6804672', 404, now(), now(), 'QUADRUPLO');

INSERT INTO hospedes (id, created_at, updated_at, nome, documento, telefone, data_nascimento)
VALUES ('e420eb03-96f2-4c10-9720-c43a0aedff95', now(), now(), 'JOÃO SILVA', '12345678901', '11987654321', '1990-03-15'),
       ('91388db7-2021-40d2-abf3-5a30876dcbe1', now(), now(), 'MARIA OLIVEIRA', '98765432100', '11987654322',
        '1985-07-22'),
       ('5134c62e-60c4-4b07-9f03-bcc3d09f4b80', now(), now(), 'PEDRO ALMEIDA', '19283746509', '11987654323',
        '1993-11-05'),
       ('424e8ea7-d709-4549-ab4b-0aa5a6804672', now(), now(), 'ANA PEREIRA', '12309874561', '11987654324',
        '1992-02-12'),
       ('e423d6de-3536-4d7d-bf5d-64e4642a584d', now(), now(), 'CARLOS MOURA', '10928374658', '11987654325',
        '1987-10-30'),
       ('5ac758cd-1b79-4a5a-bcf7-098fe5996f2c', now(), now(), 'MARCELO SANTOS', '67890123456', '11987654326',
        '1989-05-18'),
       ('5503e1f4-97f3-4561-b120-2ff2aa40c5b6', now(), now(), 'LUCAS COSTA', '54321098765', '11987654327',
        '1991-08-25'),
       ('b095d340-644e-421d-a6e9-c487415dae62', now(), now(), 'PAULA FERNANDES', '45678912345', '11987654328',
        '1988-04-09'),
       ('6868e38d-a677-4598-a32c-34112685d893', now(), now(), 'GABRIELA RIBEIRO', '23456789012', '11987654329',
        '1994-12-03'),
       ('1065bb22-eac4-459b-b6c8-d8fe9c3f6ab7', now(), now(), 'ROBERTO LIMA', '98765432109', '11987654330',
        '1983-01-20'),
       ('80eca2e7-ee26-45bb-8978-222cb90019a3', now(), now(), 'JULIANA GOMES', '19283746501', '11987654331',
        '1995-09-16'),
       ('571657d9-fe6a-4f2c-bd14-17f038ad92f5', now(), now(), 'VITOR SOUSA', '10928374659', '11987654332',
        '1990-12-13'),
       ('35dc8133-28ed-442e-a437-427a84bb8e2d', now(), now(), 'MARINA ALMEIDA', '67890123457', '11987654333',
        '1986-06-30'),
       ('3bb4d77f-5279-4998-b478-50de628d5234', now(), now(), 'FELIPE MARTINS', '54321098766', '11987654334',
        '1992-10-11'),
       ('70e84155-8387-419a-979c-d9a0fc6c9c20', now(), now(), 'RENATA CUNHA', '23456789013', '11987654335',
        '1988-02-17'),
       ('c7cb40be-d45e-464e-8880-8d8aaee0831c', now(), now(), 'RICARDO ROCHA', '98765432108', '11987654336',
        '1991-05-23'),
       ('082f197b-886d-4c58-8a6e-2974fb848bba', now(), now(), 'SANDRA PINHEIRO', '19283746502', '11987654337',
        '1984-11-27'),
       ('e8e40e3e-ef78-4b34-b38b-7ab5c91ebc31', now(), now(), 'MARCOS FERREIRA', '10928374660', '11987654338',
        '1993-07-09'),
       ('dfc9fb6f-bc42-428e-8b09-9d80db85cf0e', now(), now(), 'CAMILA COSTA', '67890123458', '11987654339',
        '1995-03-04'),
       ('a1f01156-aab2-49a8-b766-1f7b2a5d2c92', now(), now(), 'JOSÉ VIEIRA', '54321098767', '11987654340',
        '1982-09-29');

INSERT INTO reservas (id, created_at, updated_at, data_entrada, data_saida, check_in, check_out, numero_pessoas,
                      necessita_estacionamento, valor, valor_multa, hospede_id, quarto_id)
VALUES ('a575f4e6-83c6-4f86-809e-3dd40af0a2f5', '2025-03-31 03:27:05.044547', '2025-03-31 03:27:05.044547',
        '2025-04-01', '2025-04-04', null, null, 1, false, 480.0000, null, 'e420eb03-96f2-4c10-9720-c43a0aedff95',
        'e420eb03-96f2-4c10-9720-c43a0aedff95');
INSERT INTO reservas (id, created_at, updated_at, data_entrada, data_saida, check_in, check_out, numero_pessoas,
                      necessita_estacionamento, valor, valor_multa, hospede_id, quarto_id)
VALUES ('018d4054-2e21-404d-bb5a-beebf71b1b52', '2025-03-31 03:28:01.679293', '2025-03-31 03:28:01.679293',
        '2025-04-02', '2025-04-03', null, null, 2, false, 480.0000, null, '91388db7-2021-40d2-abf3-5a30876dcbe1',
        '91388db7-2021-40d2-abf3-5a30876dcbe1');
INSERT INTO reservas (id, created_at, updated_at, data_entrada, data_saida, check_in, check_out, numero_pessoas,
                      necessita_estacionamento, valor, valor_multa, hospede_id, quarto_id)
VALUES ('2ad0c813-6239-457a-afb0-0bf74b4c37ca', '2025-03-31 03:28:23.466432', '2025-03-31 03:28:23.466432',
        '2025-03-31', '2025-04-02', null, null, 4, false, 1440.0000, null, '5134c62e-60c4-4b07-9f03-bcc3d09f4b80',
        '5134c62e-60c4-4b07-9f03-bcc3d09f4b80');