CREATE TABLE topicos (
	id BIGSERIAL PRIMARY KEY,
	titulo VARCHAR(100) NOT NULL,
	mensagem VARCHAR(255) NOT NULL,
	data String NOT NULL,
	estado BOOLEAN NOT NULL,
	autor VARCHAR(100) NOT NULL UNIQUE,
 	curso VARCHAR(100) NOT NULL,	
);