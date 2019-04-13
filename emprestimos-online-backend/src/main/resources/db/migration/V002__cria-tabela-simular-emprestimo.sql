CREATE TABLE simular_emprestimo (
	numero_contrato INT(6) PRIMARY KEY,
	cpf_cliente INT(11) NOT NULL,
	data_simulacao DATETIME NOT NULL,
	data_validade_simulacao DATE NOT NULL,
	valor_contrato DECIMAL(11,2) NOT NULL,
	quantidade_parcelas INT NOT NULL,
	valor_parcela DECIMAL(11,2) NOT NULL,
	taxa_juros_emprestimo DECIMAL(11,2) NOT NULL,
	FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;