INSERT INTO utente (nome_utente, email, telefono, ruolo)
VALUES ('mario.rossi', 'mario@example.com', '3331234567', 'ADMIN');

INSERT INTO utente (nome_utente, email, telefono, ruolo)
VALUES ('anna.bianchi', 'anna@example.com', '3459876543', 'USER');



INSERT INTO avvistamento (id, data_ora, specie, razza, descrizione_fisica, foto, informazioni_extra, cod_luogo, cod_utente, cod_status, stato_salute, azioni_intraprese)
VALUES (
    1,
    '2025-05-02T14:30:00',
    'Cane',
    'Labrador',
    'Taglia media, nero, con collare rosso',
    '/upload/foto1.png',
    'Avvistato vicino al parco alle 14:00',
    null,
    null,
    null,
    'Buona',
    'Contattato canile municipale'
);
