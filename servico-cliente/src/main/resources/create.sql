DROP TABLE CLIENTES.CLIENTE_EMAIL;
DROP TABLE CLIENTES.CLIENTE_TELEFONE;
DROP TABLE CLIENTES.EMAIL;
DROP TABLE CLIENTES.TELEFONE;
DROP TABLE CLIENTES.CLIENTE;
DROP TABLE CLIENTES.ENDERECO;
DROP TABLE CLIENTES.USUARIO;
DROP SCHEMA CLIENTES;

CREATE SCHEMA clientes; 

CREATE TABLE clientes.usuario (
    id bigserial NOT NULL PRIMARY KEY,
    nome character varying(150),
    login character varying(100),
    senha character varying(255),
	role character varying(255)
);

CREATE TABLE clientes.endereco (
	id bigserial NOT NULL PRIMARY KEY,
	cep character varying(10),
	logradouro character varying(150),
	bairro character varying(150),
	cidade character varying(150),
	uf character varying(5),
	complemento character varying(150)
);

CREATE TABLE clientes.cliente (
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150),
	cpf character varying(20),
	data_cadastro date,
	data_ultima_atualizacao date,
	id_usuario bigint REFERENCES clientes.usuario(id),
	id_endereco bigint REFERENCES clientes.endereco(id)
);

CREATE TABLE clientes.telefone (
	id bigserial NOT NULL PRIMARY KEY,
	numero character varying(15),
	tipo character varying(20)
);

CREATE TABLE clientes.email (
	id bigserial NOT NULL PRIMARY KEY,
	email character varying(150)
);

CREATE TABLE clientes.cliente_telefone (
	cliente_id bigserial,
	telefone_id bigserial
);

CREATE TABLE clientes.cliente_email (
	cliente_id bigserial,
	email_id bigserial
);


INSERT INTO clientes.usuario (nome, login, senha, role) values ('Administrador','admin','123456','ADMIN');
INSERT INTO clientes.usuario (nome, login, senha, role) values ('Usuario Comum','comum','123456','COMUM');