CREATE TABLE parcela_emprestimo (
	numero_contrato INT(6),
	numero_da_parcela INT,
	valor_parcela DECIMAL(11,2) NOT NULL,
	data_vencimento DATE NOT NULL,
	PRIMARY KEY (numero_contrato, numero_da_parcela),
	FOREIGN KEY (numero_contrato) REFERENCES contratar_emprestimo(numero_contrato)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;