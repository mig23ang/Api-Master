DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS categoria;

CREATE TABLE categoria (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

INSERT INTO categoria (id, nombre)
VALUES (1, 'pescados'), (2, 'mariscos'), (3, 'carnes');

CREATE TABLE producto (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL,
    categoria_id BIGINT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

INSERT INTO producto (id, nombre, descripcion, precio, categoria_id)
VALUES (1, 'mojarra', 'Mojarra de rio', 10.99, 1), (2, 'calamar', 'de mar', 25.99, 2), (3, 'pierna de res', 'Res', 12.50, 3), (4, 'pollo semi criollo', NULL, 5.99, 3);
