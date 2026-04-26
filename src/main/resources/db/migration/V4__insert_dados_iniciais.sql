INSERT INTO clientes (nome, telefone, created_at) VALUES
                                                      ('João Silva',       '22999101010', NOW()),
                                                      ('Maria Oliveira',   '22988202020', NOW()),
                                                      ('Carlos Souza',     '21977303030', NOW()),
                                                      ('Ana Paula Lima',   '21966404040', NOW()),
                                                      ('Pedro Henrique',   '22955505050', NOW());

INSERT INTO servicos (nome_servico, duracao, preco, ativo) VALUES
                                                               ('Corte de Cabelo',         30, 35.00,  1),
                                                               ('Barba',                   20, 25.00,  1),
                                                               ('Corte + Barba',           50, 55.00,  1),
                                                               ('Hidratação',              60, 80.00,  1),
                                                               ('Progressiva',            120, 180.00, 1),
                                                               ('Sobrancelha',             15, 20.00,  1),
                                                               ('Platinado',              180, 250.00, 0);

INSERT INTO agendamentos (inicio, termino, status, cliente_id, servico_id, created_at, updated_at) VALUES
                                                                                                       ('2026-04-14 09:00:00', '2026-04-14 09:30:00', 'AGENDADO',  1, 1, NOW(), NOW()),
                                                                                                       ('2026-04-14 09:30:00', '2026-04-14 09:50:00', 'AGENDADO',  2, 2, NOW(), NOW()),
                                                                                                       ('2026-04-14 10:00:00', '2026-04-14 10:50:00', 'AGENDADO',  3, 3, NOW(), NOW()),
                                                                                                       ('2026-04-14 11:00:00', '2026-04-14 12:00:00', 'AGENDADO',  4, 4, NOW(), NOW()),
                                                                                                       ('2026-04-14 14:00:00', '2026-04-14 14:30:00', 'CANCELADO', 5, 1, NOW(), NOW()),
                                                                                                       ('2026-04-15 09:00:00', '2026-04-15 09:30:00', 'AGENDADO',  1, 1, NOW(), NOW()),
                                                                                                       ('2026-04-15 10:00:00', '2026-04-15 10:50:00', 'AGENDADO',  2, 3, NOW(), NOW()),
                                                                                                       ('2026-04-15 11:00:00', '2026-04-15 12:00:00', 'COMPLETO',  3, 4, NOW(), NOW()),
                                                                                                       ('2026-04-16 09:00:00', '2026-04-16 12:00:00', 'AGENDADO',  4, 5, NOW(), NOW()),
                                                                                                       ('2026-04-16 14:00:00', '2026-04-16 14:15:00', 'AGENDADO',  5, 6, NOW(), NOW());