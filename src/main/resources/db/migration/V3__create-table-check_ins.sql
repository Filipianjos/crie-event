CREATE TABLE check_ins (
    id INTEGER NOT NULL PRIMARY KEY,
    data_checkin TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_participante VARCHAR(255) NOT NULL,
    CONSTRAINT check_ins_id_participante_id_fkey FOREIGN KEY (id_participante) REFERENCES participantes (id) ON DELETE RESTRICT ON UPDATE CASCADE
);