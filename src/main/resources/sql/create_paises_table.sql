CREATE TABLE IF NOT EXISTS paises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS modalidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE paises_modalidade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pais INT,
    id_modalidade INT,
    UNIQUE (id_pais, id_modalidade),
    FOREIGN KEY (id_pais) REFERENCES paises(id) ON DELETE CASCADE,
    FOREIGN KEY (id_modalidade) REFERENCES modalidades(id) ON DELETE CASCADE
);