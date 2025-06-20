INSERT INTO utente (nome_utente, email, telefono, password, ruolo) VALUES 
('mario.rossi', 'mario@example.com', '3331234567', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'ADMIN'),
('anna.bianchi', 'anna@example.com', '3459876543', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'USER'),
('lucia.verdi', 'lucia@example.com', '3471122334', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'USER'),
('giovanni.neri', 'giovanni@example.com', '3399988776', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'USER'),
('elena.martini', 'elena@example.com', '3205544332', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'USER'),
('fabio.conti', 'fabio@example.com', '3286677885', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'USER'),
('sara.galli', 'sara@example.com', '3317766554', '$2a$10$nbt3iT4Zc2Sy5DQLmG7P2eeui2AsAilZgc9TrKk/vYCuMfexwBYza', 'USER');


INSERT INTO avvistamento (id, data_ora, specie, razza, descrizione_fisica, foto, informazioni_extra, luogo, latitudine, longitudine, cod_status, cod_utente, stato_salute, azioni_intraprese) VALUES 
(1, '2025-05-19T08:45:00', 'Cane', 'Labrador', 'Labrador chocolate con occhi verdi',
    'https://www.quattrozampeinfamiglia.it/wp-content/uploads/2024/10/cani-da-riporto.jpg',
    'Senza collare, si aggirava nei pressi di un parcheggio', 'Via del Corso, Rome, Italy', 41.902442, 12.479821, 'ATTIVO', 7, 'Sembra in buona salute ma diffidente',
    'Segnalato su app e lasciato un po’ di cibo'
),
(2, '2025-04-19T08:46:00', 'Cane', 'Labrador', 'Labrador chocolate con occhi verdi',
    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR750PpumOwwn4UF-dwnCr8mKna7BpBfGHGqw&s',
    'Senza collare, si aggirava nei pressi di un parcheggio', 'Via del Corso, Rome, Italy', 41.9000, 12.4800, 'ATTIVO', 2, 'Sembra in buona salute ma diffidente',
    'Segnalato su app e lasciato un po’ di cibo'
),
(3, '2025-06-10T09:20:00', 'Cane', 'Golden Retriever', 'Golden retriever adulto, mantello lucido', 
 'https://upload.wikimedia.org/wikipedia/commons/1/1d/Golden_retriever_stehfoto.jpg', 
 'Con collare blu, molto socievole', 'Via Emilia, Bologna, Italy', 44.493, 11.342, 'ATTIVO', 3, 'Buona salute, buono stato fisico', 'Rinvenuto e successivamente microchippato'),
(4, '2025-06-11T18:05:00', 'Cane', 'German Shepherd', 'Pastore tedesco giovane, pelo corto', 
 'https://pet-health-content-media.chewy.com/wp-content/uploads/2024/09/11180014/202105German-Shepherd-1358309706-scaled-1.jpg', 
 'Senze collare, sembrava smarrito', 'Parco Montagnola, Bologna, Italy', 44.490, 11.336, 'ATTIVO', 4, 'Apparentemente sano ma agitato', 'Segnalato a vigili e forniti cibo'),
(5, '2025-06-12T07:45:00', 'Cane', 'Beagle', 'Beagle tricolore, orecchie lunghe', 
 'https://images.ctfassets.net/550nf1gumh01/6zwLKAfltciBljUmEdYP3n/21191083f31cb0a43647cddbf29de913/iStock-1411469044.jpg?q=90&w=1240', 
 'Si aggirava vicino a una scuola', 'Via Ugo Bassi, Bologna, Italy', 44.497, 11.345, 'ATTIVO', 5, 'Sano e vivace', 'Accompagnato a centro rifugio locale'),
(6, '2025-06-13T14:30:00', 'Cane', 'Bulldog Francese', 'Bulldog francese color crema', 
 'https://citynews-today.stgy.ovh/~media/horizontal-low/55160363608053/bulldog-francese-3.jpg', 
 'Con pettorina rosa, sembra smarrita', 'Quartiere Santo Stefano, Bologna, Italy', 44.496, 11.348, 'ATTIVO', 6, 'Respiro affannato ma stabile', 'Contattato proprietario tramite microchip'),
(7, '2025-06-14T20:15:00', 'Cane', 'Border Collie', 'Border collie adulto, pelo bicolore nero-bianco', 
 'https://clinicaveterinariagaia.com/wp-content/uploads/2021/08/clinica-veterinaria-gaia-ancona-border-collie.jpg', 
 'Senze collare, ma con medaglietta', 'Parco della Montagnola, Bologna, Italy', 44.490, 11.336, 'ATTIVO', 1, 'In forma, ben curato', 'Fornito acqua, attesa rientro proprietario');

INSERT INTO Denuncia (id, data_ora, specie, razza, descrizione_fisica, foto, informazioni_extra, luogo, latitudine, longitudine, cod_status, cod_utente) VALUES 
(8, '2025-05-19T08:45:00', 'Cane', 'Labrador', 'Labrador chocolate con occhi verdi', 
    'https://www.quattrozampeinfamiglia.it/wp-content/uploads/2024/10/cani-da-riporto.jpg',
    'Senza collare, si aggirava nei pressi di un parcheggio', 'Via del Corso, Rome, Italy', 41.9000, 12.4800, 'ATTIVO', 1
),
(9, '2025-05-20T10:30:00', 'Cane', 'Beagle', 'Beagle tricolore, orecchie lunghe', 
    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xuaBlFwunZ8wEaETTt7SwXV4kIqXkgdP1g&s',
    'Avvistato correre lungo la pista ciclabile', 'Via Appia Nuova, Rome, Italy', 41.8719, 12.5674, 'ATTIVO', 2),
(11, '2025-05-22T07:15:00', 'Gatto', 'Europeo', 'Gatto europeo grigio tigrato, occhi gialli', 
    'https://static.tecnichenuove.it/animalidacompagnia/2018/10/gatto-europeo.jpg',
    'Avvistato vicino a dei cassonetti, sembrava spaesato', 'Via Tuscolana, Rome, Italy', 41.8576, 12.5483, 'ATTIVO', 4);


SELECT setval('segnalazione_seq',
  GREATEST(
    COALESCE((SELECT MAX(id) FROM avvistamento), 0),
    COALESCE((SELECT MAX(id) FROM denuncia), 0)
  )
);