CREATE TABLE participantes (
	id VARCHAR(255) NOT NULL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	id_evento VARCHAR(255) NOT NULL,
	CONSTRAINT participantes_id_evento_fkey FOREIGN KEY (id_evento) REFERENCES eventos (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
