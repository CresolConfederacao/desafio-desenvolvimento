CREATE TABLE contratar_emprestimo (
	numero_contrato INT(6) PRIMARY KEY,
	data_contratacao DATETIME NOT NULL,
	valor_contrato DECIMAL(11,2) NOT NULL,
	quantidade_parcelas INT NOT NULL,
	taxa_juros_emprestimo DECIMAL(11,2) NOT NULL,
	iofContrato DECIMAL(11,2) NOT NULL,
	FOREIGN KEY (numero_contrato) REFERENCES simular_emprestimo(numero_contrato)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;