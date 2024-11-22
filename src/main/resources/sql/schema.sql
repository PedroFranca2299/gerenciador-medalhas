-- Script de criação da estrutura do banco de dados

-- Tabela de Países
CREATE TABLE IF NOT EXISTS paises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    participando BOOLEAN DEFAULT FALSE
);

-- Tabela de Modalidades
CREATE TABLE IF NOT EXISTS modalidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT FALSE
);

-- Tabela de Etapas
CREATE TABLE IF NOT EXISTS etapas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela de Resultados
CREATE TABLE IF NOT EXISTS resultados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modalidade_id INT NOT NULL,
    etapa_id INT NOT NULL,
    data_resultado DATETIME NOT NULL,
    FOREIGN KEY (modalidade_id) REFERENCES modalidades(id),
    FOREIGN KEY (etapa_id) REFERENCES etapas(id)
);

-- Tabela de Participações em Resultados
CREATE TABLE IF NOT EXISTS participacoes_resultado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resultado_id INT NOT NULL,
    pais_id INT NOT NULL,
    posicao INT, -- NULL significa que participou mas não ficou no pódio
    FOREIGN KEY (resultado_id) REFERENCES resultados(id),
    FOREIGN KEY (pais_id) REFERENCES paises(id)
);