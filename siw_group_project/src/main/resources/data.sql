INSERT INTO utente (nome_utente, email, telefono, password, ruolo)
VALUES ('mario.rossi', 'mario@example.com', '3331234567', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'ADMIN');

INSERT INTO utente (nome_utente, email, telefono, ruolo)
VALUES ('anna.bianchi', 'anna@example.com', '3459876543', 'USER');

INSERT INTO avvistamento (
    id,
    data_ora,
    specie,
    razza,
    descrizione_fisica,
    foto,
    informazioni_extra,
    luogo,
    latitudine,
    longitudine,
    cod_status,
    cod_utente,
    stato_salute,
    azioni_intraprese
) VALUES (
    1001,
    '2025-05-19T08:45:00',
    'Cane',
    'Labrador',
    'Labrador chocolate con occhi verdi',
    'https://www.quattrozampeinfamiglia.it/wp-content/uploads/2024/10/cani-da-riporto.jpg',
    'Senza collare, si aggirava nei pressi di un parcheggio',
    'Via del Corso 123, Roma',
    41.9000,
    12.4800,
    'ATTIVO',
    1,
    'Sembra in buona salute ma diffidente',
    'Segnalato su app e lasciato un po’ di cibo'
);
