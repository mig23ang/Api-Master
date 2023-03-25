-- eliminar tabla si existe
--DROP TABLE IF EXISTS producto CASCADE;
--DROP TABLE IF EXISTS categoria ;

-- Crear tabla categoria
CREATE TABLE categoria (
  id BIGINT PRIMARY KEY,
  nombre VARCHAR(255)
);

-- Crear tabla producto
CREATE TABLE producto (
  id BIGINT PRIMARY KEY,
  nombre VARCHAR(255),
  descripcion TEXT,
  precio DECIMAL,
  categoria_id BIGINT REFERENCES categoria(id)
);

-- Insertar datos en tabla categoria
INSERT INTO CATEGORIA (id, nombre) VALUES (6, 'granos');

-- Insertar datos en tabla producto
INSERT INTO PRODUCTO(id, nombre, descripcion, precio, categoria_id) VALUES (11,'pan', 'hecho de trigo', 33, 6);


