CREATE TABLE IF NOT EXISTS clientes (
	cpf TEXT PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	email TEXT NOT NULL,
	telefone TEXT NOT NULL,
	data_cadastro TEXT NOT NULL,
	data_nascimento TEXT NOT NULL,
	ativo TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS enderecos_clientes (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	estado TEXT NOT NULL,
	cidade TEXT NOT NULL,
	rua TEXT NOT NULL,
	numero INTEGER NOT NULL,
	complemento TEXT,
	cpf_cliente TEXT NOT NULL,
	FOREIGN KEY (cpf_cliente) REFERENCES clientes(cpf) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pets (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	nome TEXT NOT NULL,
	especie TEXT NOT NULL,
	data_nascimento TEXT NOT NULL,
	raca TEXT,
	sexo TEXT NOT NULL,
	tutor TEXT NOT NULL,
	FOREIGN KEY (tutor) REFERENCES clientes(cpf) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS observacoes_pets (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	pet_id INTEGER NOT NULL,
	observacao TEXT NOT NULL,
	FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS funcionarios (
	cpf TEXT PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	email TEXT NOT NULL,
	telefone TEXT NOT NULL,
	data_cadastro TEXT NOT NULL,
	data_nascimento TEXT NOT NULL,
	ativo TEXT NOT NULL,
	senha TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS enderecos_funcionarios (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	estado TEXT NOT NULL,
	cidade TEXT NOT NULL,
	rua TEXT NOT NULL,
	numero INTEGER NOT NULL,
	complemento TEXT,
	cpf_funcionarios TEXT NOT NULL,
	FOREIGN KEY (cpf_funcionarios) REFERENCES funcionarios(cpf) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS produtos (
	codigo TEXT PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	descricao TEXT,
	quantidade INTEGER NOT NULL,
	valor REAL NOT NULL,
	ultima_atualizacao TEXT NOT NULL,
	tipo TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS vendas (
	codigo TEXT PRIMARY KEY NOT NULL,
	total REAL NOT NULL,
	data_venda TEXT NOT NULL,
	cpf_cliente TEXT NOT NULL,
	funcionario_responsavel TEXT NOT NULL,
	status TEXT NOT NULL,
	FOREIGN KEY (cpf_cliente) REFERENCES clientes(cpf),
	FOREIGN KEY (funcionario_responsavel) REFERENCES funcionarios(cpf)
);

CREATE TABLE IF NOT EXISTS produtos_vendas (
	codigo_produto TEXT NOT NULL,
	codigo_venda TEXT NOT NULL,
	quantidade INTEGER NOT NULL,
    preco_unitario REAL NOT NULL,
	FOREIGN KEY (codigo_produto) REFERENCES produtos(codigo),
	FOREIGN KEY (codigo_venda) REFERENCES vendas(codigo)
);

CREATE TABLE IF NOT EXISTS veterinarios (
	cpf TEXT PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	email TEXT NOT NULL,
	telefone TEXT NOT NULL,
	data_cadastro TEXT NOT NULL,
	data_nascimento TEXT NOT NULL,
	ativo TEXT NOT NULL,
	especialidade TEXT NOT NULL,
	crmv TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS enderecos_veterinarios (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	estado TEXT NOT NULL,
	cidade TEXT NOT NULL,
	rua TEXT NOT NULL,
	numero INTEGER NOT NULL,
	complemento TEXT,
	cpf_veterinario TEXT NOT NULL,
	FOREIGN KEY (cpf_veterinario) REFERENCES veterinarios(cpf)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS consultas (
	codigo TEXT PRIMARY KEY NOT NULL,
	pet_id INTEGER NOT NULL,
	cpf_veterinario TEXT NOT NULL,
	data_consulta TEXT NOT NULL,
    data_fechamento TEXT,
	status TEXT NOT NULL,
	FOREIGN KEY (pet_id) REFERENCES pets(id),
	FOREIGN KEY (cpf_veterinario) REFERENCES veterinarios(cpf)
);

CREATE TABLE IF NOT EXISTS produtos_consultas (
	codigo_produto TEXT NOT NULL,
	codigo_consulta TEXT NOT NULL,
	quantidade INTEGER NOT NULL,
    preco_unitario REAL NOT NULL,
	FOREIGN KEY (codigo_produto) REFERENCES produtos(codigo),
	FOREIGN KEY (codigo_consulta) REFERENCES consultas(codigo)
);