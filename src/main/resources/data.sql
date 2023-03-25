-- Crear tabla categoria
CREATE TABLE categoria (
  id BIGINT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL
);

-- Insertar datos en tabla categoria
INSERT INTO categoria (id, nombre)
VALUES 
  (1, 'Categoría 1'),
  (2, 'Categoría 2'),
  (3, 'Categoría 3');

-- Crear tabla producto
CREATE TABLE producto (
  id BIGINT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  descripcion VARCHAR(255),
  precio DECIMAL(10,2) NOT NULL,
  categoria_id BIGINT NOT NULL,
  FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- Insertar datos en tabla producto
INSERT INTO producto (id, nombre, descripcion, precio, categoria_id)
VALUES 
  (1, 'Producto 1', 'Descripción del producto 1', 10.99, 1),
  (2, 'Producto 2', 'Descripción del producto 2', 25.99, 2),
  (3, 'Producto 3', 'Descripción del producto 3', 12.50, 1),
  (4, 'Producto 4', 'Descripción del producto 4', 5.99, 3);
