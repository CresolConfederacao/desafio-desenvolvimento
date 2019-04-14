CREATE TABLE cliente (
	cpf BIGINT(11) PRIMARY KEY,
	nome varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	CONSTRAINT unique_cpf_cliente UNIQUE (cpf),
	CONSTRAINT unique_email_cliente UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;