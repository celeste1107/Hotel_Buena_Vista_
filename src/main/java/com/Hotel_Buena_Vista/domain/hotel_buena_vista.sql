/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20)
);
CREATE TABLE huesped (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);
CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    huesped_id BIGINT NOT NULL,
    FOREIGN KEY (huesped_id) REFERENCES huesped(id) ON DELETE CASCADE
);


-- Inserción de usuarios
-- Usuarios
INSERT INTO usuario (nombre, correo, password, telefono) VALUES
('Luis Mora Gonzales','moraluis@gmail.com','$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.','8749-7478'),
('Sofia Cortes Fallas','cortesfallas@gmail.com','$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi','9383- 8789'),
('Alejandro Cordero Cerdas','alecordero.com','$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO','8393-8936');

-- Huespedes
INSERT INTO huesped (nombre, apellido, email) VALUES
('Ana', 'Gomez', 'ana.gomez@email.com'),
('Carlos', 'Perez', 'carlos.perez@email.com'),
('Lucia', 'Ramirez', 'lucia.ramirez@email.com');

