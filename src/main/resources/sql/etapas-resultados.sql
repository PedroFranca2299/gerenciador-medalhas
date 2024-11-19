-- Tabela de Etapas (ex: oitavas, quartas, semi, final)
CREATE TABLE etapas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Dados iniciais de etapas
INSERT INTO etapas (nome) VALUES
('Oitavas de Final'),
('Quartas de Final'),
('Semifinal'),
('Final');

-- Tabela de Resultados
CREATE TABLE resultados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modalidade_id INT NOT NULL,
    etapa_id INT NOT NULL,
    data_resultado DATETIME NOT NULL,
    FOREIGN KEY (modalidade_id) REFERENCES modalidades(id),
    FOREIGN KEY (etapa_id) REFERENCES etapas(id)
);

-- Tabela de Participações em Resultados
-- Esta tabela permite múltiplos países por resultado
CREATE TABLE participacoes_resultado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resultado_id INT NOT NULL,
    pais_id INT NOT NULL,
    posicao INT, -- NULL significa que participou mas não ficou no pódio
    FOREIGN KEY (resultado_id) REFERENCES resultados(id),
    FOREIGN KEY (pais_id) REFERENCES paises(id)
);