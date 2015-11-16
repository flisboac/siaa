
CREATE TABLE pessoa
(
  id bigint NOT NULL,
  data_nascimento date NOT NULL,
  nome_completo character varying(128) NOT NULL,
  CONSTRAINT pk_pessoa PRIMARY KEY (id),
  CONSTRAINT uq_pessoa_nome UNIQUE (nome_completo, data_nascimento)
);

CREATE TABLE autenticacao
(
  tipo character varying(31) NOT NULL,
  id bigint NOT NULL,
  data_ultima_alteracao timestamp without time zone,
  sal oid,
  senha oid,
  id_pessoa bigint NOT NULL,
  CONSTRAINT pk_autenticacao PRIMARY KEY (id),
  CONSTRAINT fk_autenticacao_id_pessoa FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_autenticacao UNIQUE (id_pessoa, tipo)
);

CREATE TABLE perfil
(
  tipo character varying(31) NOT NULL,
  id bigint NOT NULL,
  data_desativacao timestamp without time zone,
  id_pessoa bigint NOT NULL,
  CONSTRAINT pk_perfil PRIMARY KEY (id),
  CONSTRAINT fk_perfil_id_pessoa FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE perfil_aluno
(
  matricula character varying(10) NOT NULL,
  id_perfil bigint NOT NULL,
  CONSTRAINT pk_perfil_aluno PRIMARY KEY (id_perfil),
  CONSTRAINT fk_perfil_aluno_id_perfil FOREIGN KEY (id_perfil)
      REFERENCES perfil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_perfil_aluno_matricula UNIQUE (matricula)
);

CREATE TABLE coordenacao
(
  id bigint NOT NULL,
  codigo character varying(3) NOT NULL,
  nome character varying(3) NOT NULL,
  id_perfil_coordenador bigint NOT NULL,
  CONSTRAINT pk_coordenacao PRIMARY KEY (id),
  CONSTRAINT fk_coordenacao_id_coordenador FOREIGN KEY (id_perfil_coordenador)
      REFERENCES perfil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_coordenacao_codigo UNIQUE (codigo)
);

CREATE TABLE login
(
  id bigint NOT NULL,
  data_desativacao timestamp without time zone,
  nome character varying(32) NOT NULL,
  id_pessoa bigint NOT NULL,
  CONSTRAINT pk_login PRIMARY KEY (id),
  CONSTRAINT fk_login_id_pessoa FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_login UNIQUE (nome),
  CONSTRAINT uq_login_id_pessoa UNIQUE (id_pessoa)
);

CREATE TABLE materia
(
  id bigint NOT NULL,
  codigo character varying(32) NOT NULL,
  metodo_avaliacao character varying(255) NOT NULL,
  nome character varying(64) NOT NULL,
  id_coordenacao_origem bigint NOT NULL,
  CONSTRAINT pk_materia PRIMARY KEY (id),
  CONSTRAINT fk_materia_id_coordenacao_origem FOREIGN KEY (id_coordenacao_origem)
      REFERENCES coordenacao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_materia UNIQUE (codigo, id_coordenacao_origem)
);

CREATE TABLE turma
(
  id bigint NOT NULL,
  codigo character varying(64) NOT NULL,
  data_criacao timestamp without time zone,
  numero_maximo_alunos integer NOT NULL,
  id_materia bigint NOT NULL,
  id_professor_responsavel bigint NOT NULL,
  CONSTRAINT pk_turma PRIMARY KEY (id),
  CONSTRAINT fk_turma_id_materia FOREIGN KEY (id_materia)
      REFERENCES materia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_turma_id_professor_responsavel FOREIGN KEY (id_professor_responsavel)
      REFERENCES perfil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_turma UNIQUE (codigo)
);

CREATE TABLE turma_aluno
(
  id bigint NOT NULL,
  nota_01_ausente boolean,
  nota_01_data timestamp without time zone,
  nota_01 numeric(4,2),
  nota_02_ausente boolean,
  nota_02_data timestamp without time zone,
  nota_02 numeric(4,2),
  nota_03_ausente boolean,
  nota_03_data timestamp without time zone,
  nota_03 numeric(4,2),
  nota_04_ausente boolean,
  nota_04_data timestamp without time zone,
  nota_04 numeric(4,2),
  media_final numeric(4,2),
  media_preliminar numeric(4,2),
  status character varying(255),
  id_aluno bigint NOT NULL,
  id_turma bigint NOT NULL,
  CONSTRAINT turma_aluno_pkey PRIMARY KEY (id),
  CONSTRAINT fk_turma_aluno_id_aluno FOREIGN KEY (id_aluno)
      REFERENCES perfil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_turma_aluno_id_turma FOREIGN KEY (id_turma)
      REFERENCES turma (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_turma_aluno UNIQUE (id_turma, id_aluno)
);
