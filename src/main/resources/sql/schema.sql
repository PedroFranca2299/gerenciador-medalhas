-- Tabela de Países
CREATE TABLE IF NOT EXISTS paises (
    id INT AUTO_INCREMENT,  -- H2 understands AUTO_INCREMENT
    nome VARCHAR(255) NOT NULL,
    participando BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

-- Tabela de Modalidades
CREATE TABLE IF NOT EXISTS modalidades (
    id INT AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

-- Tabela de Etapas
CREATE TABLE IF NOT EXISTS etapas (
    id INT AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela de Resultados
CREATE TABLE IF NOT EXISTS resultados (
    id INT AUTO_INCREMENT,
    modalidade_id INT NOT NULL,
    etapa_id INT NOT NULL,
    data_resultado TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (modalidade_id) REFERENCES modalidades(id),
    FOREIGN KEY (etapa_id) REFERENCES etapas(id)
);

-- Tabela de Participações em Resultados
CREATE TABLE IF NOT EXISTS participacoes_resultado (
    id INT AUTO_INCREMENT,
    resultado_id INT NOT NULL,
    pais_id INT NOT NULL,
    posicao INT,
    PRIMARY KEY (id),
    FOREIGN KEY (resultado_id) REFERENCES resultados(id),
    FOREIGN KEY (pais_id) REFERENCES paises(id)
);