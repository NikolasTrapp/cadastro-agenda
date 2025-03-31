INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES (gen_random_uuid(), 101, now(), now(), 'INDIVIDUAL');
INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES (gen_random_uuid(), 202, now(), now(), 'DUPLO');
INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES (gen_random_uuid(), 303, now(), now(), 'TRIPLO');
INSERT INTO quartos (id, numero_quarto, created_at, updated_at, tipo_quarto)
VALUES (gen_random_uuid(), 404, now(), now(), 'QUADRUPLO');

INSERT INTO hospedes (id, created_at, updated_at, nome, documento, telefone, data_nascimento)
VALUES (gen_random_uuid(), now(), now(), 'JOÃO SILVA', '12345678901', '11987654321', '1990-03-15'),
       (gen_random_uuid(), now(), now(), 'MARIA OLIVEIRA', '98765432100', '11987654322', '1985-07-22'),
       (gen_random_uuid(), now(), now(), 'PEDRO ALMEIDA', '19283746509', '11987654323', '1993-11-05'),
       (gen_random_uuid(), now(), now(), 'ANA PEREIRA', '12309874561', '11987654324', '1992-02-12'),
       (gen_random_uuid(), now(), now(), 'CARLOS MOURA', '10928374658', '11987654325', '1987-10-30'),
       (gen_random_uuid(), now(), now(), 'MARCELO SANTOS', '67890123456', '11987654326', '1989-05-18'),
       (gen_random_uuid(), now(), now(), 'LUCAS COSTA', '54321098765', '11987654327', '1991-08-25'),
       (gen_random_uuid(), now(), now(), 'PAULA FERNANDES', '45678912345', '11987654328', '1988-04-09'),
       (gen_random_uuid(), now(), now(), 'GABRIELA RIBEIRO', '23456789012', '11987654329', '1994-12-03'),
       (gen_random_uuid(), now(), now(), 'ROBERTO LIMA', '98765432109', '11987654330', '1983-01-20'),
       (gen_random_uuid(), now(), now(), 'JULIANA GOMES', '19283746501', '11987654331', '1995-09-16'),
       (gen_random_uuid(), now(), now(), 'VITOR SOUSA', '10928374659', '11987654332', '1990-12-13'),
       (gen_random_uuid(), now(), now(), 'MARINA ALMEIDA', '67890123457', '11987654333', '1986-06-30'),
       (gen_random_uuid(), now(), now(), 'FELIPE MARTINS', '54321098766', '11987654334', '1992-10-11'),
       (gen_random_uuid(), now(), now(), 'RENATA CUNHA', '23456789013', '11987654335', '1988-02-17'),
       (gen_random_uuid(), now(), now(), 'RICARDO ROCHA', '98765432108', '11987654336', '1991-05-23'),
       (gen_random_uuid(), now(), now(), 'SANDRA PINHEIRO', '19283746502', '11987654337', '1984-11-27'),
       (gen_random_uuid(), now(), now(), 'MARCOS FERREIRA', '10928374660', '11987654338', '1993-07-09'),
       (gen_random_uuid(), now(), now(), 'CAMILA COSTA', '67890123458', '11987654339', '1995-03-04'),
       (gen_random_uuid(), now(), now(), 'JOSÉ VIEIRA', '54321098767', '11987654340', '1982-09-29');
