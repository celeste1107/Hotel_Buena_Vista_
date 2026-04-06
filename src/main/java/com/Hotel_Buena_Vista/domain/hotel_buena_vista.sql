-- ============================================================
--  Hotel Buena Vista — Esquema MySQL
--  Base de datos: hotel_buena_vista
-- ============================================================
 
CREATE DATABASE IF NOT EXISTS hotel_buena_vista
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
 
USE hotel_buena_vista;
 
-- ------------------------------------------------------------
-- 1. USUARIOS
--    Personal del hotel que opera el sistema
-- ------------------------------------------------------------
CREATE TABLE usuarios (
  id_usuario     INT            NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(100)   NOT NULL,
  email          VARCHAR(150)   NOT NULL UNIQUE,
  password_hash  VARCHAR(255)   NOT NULL,
  rol            ENUM('admin', 'recepcionista', 'supervisor') NOT NULL DEFAULT 'recepcionista',
  created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario),
  INDEX idx_email (email),
  INDEX idx_rol   (rol)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 2. INICIO_SESION
--    Historial de sesiones del personal
-- ------------------------------------------------------------
CREATE TABLE inicio_sesion (
  id_sesion     INT           NOT NULL AUTO_INCREMENT,
  id_usuario    INT           NOT NULL,
  fecha_inicio  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  token         VARCHAR(255)  NOT NULL,
  estado        ENUM('activa', 'cerrada', 'expirada') NOT NULL DEFAULT 'activa',
  PRIMARY KEY (id_sesion),
  INDEX idx_usuario (id_usuario),
  INDEX idx_token   (token),
  CONSTRAINT fk_sesion_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;
-- ------------------------------------------------------------
-- 3. HUESPEDES
--    Clientes del hotel
-- ------------------------------------------------------------
CREATE TABLE huespedes (
  id_huesped          INT           NOT NULL AUTO_INCREMENT,
  nombre              VARCHAR(100)  NOT NULL,
  apellido            VARCHAR(100)  NOT NULL,
  email               VARCHAR(150)  NULL,
  telefono            VARCHAR(20)   NULL,
  documento_identidad VARCHAR(50)   NOT NULL,
  tipo_documento      ENUM('cedula', 'pasaporte', 'licencia') NOT NULL DEFAULT 'cedula',
  nacionalidad        VARCHAR(80)   NULL,
  fecha_nacimiento    DATE          NULL,
  created_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_huesped),
  UNIQUE INDEX idx_documento (documento_identidad),
  INDEX idx_apellido (apellido),
  INDEX idx_email    (email)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- 4. HABITACIONES
--    Inventario de habitaciones del hotel
-- ------------------------------------------------------------
CREATE TABLE habitaciones (
  id_habitacion  INT             NOT NULL AUTO_INCREMENT,
  numero         VARCHAR(10)     NOT NULL UNIQUE,
  piso           TINYINT         NOT NULL,
  tipo           ENUM('sencilla', 'doble', 'suite', 'familiar', 'presidencial') NOT NULL,
  capacidad      TINYINT         NOT NULL DEFAULT 2,
  precio_noche   DECIMAL(10, 2)  NOT NULL,
  estado         ENUM('disponible', 'ocupada', 'mantenimiento', 'reservada') NOT NULL DEFAULT 'disponible',
  descripcion    TEXT            NULL,
  PRIMARY KEY (id_habitacion),
  INDEX idx_estado (estado),
  INDEX idx_tipo   (tipo),
  INDEX idx_piso   (piso)
) ENGINE=InnoDB;
 
-- ------------------------------------------------------------
-- 5. RESERVAS
--    Reservaciones de huéspedes
-- ------------------------------------------------------------
CREATE TABLE reservas (
  id_reserva     INT             NOT NULL AUTO_INCREMENT,
  id_huesped     INT             NOT NULL,
  id_habitacion  INT             NOT NULL,
  fecha_entrada  DATE            NOT NULL,
  fecha_salida   DATE            NOT NULL,
  num_huespedes  TINYINT         NOT NULL DEFAULT 1,
  precio_total   DECIMAL(10, 2)  NOT NULL,
  estado         ENUM('pendiente', 'confirmada', 'cancelada', 'completada') NOT NULL DEFAULT 'pendiente',
  observaciones  TEXT            NULL,
  created_at     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_reserva),
  INDEX idx_huesped    (id_huesped),
  INDEX idx_habitacion (id_habitacion),
  INDEX idx_fechas     (fecha_entrada, fecha_salida),
  INDEX idx_estado     (estado),
  CONSTRAINT fk_reserva_huesped
    FOREIGN KEY (id_huesped)    REFERENCES huespedes   (id_huesped)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_reserva_habitacion
    FOREIGN KEY (id_habitacion) REFERENCES habitaciones (id_habitacion)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT chk_fechas CHECK (fecha_salida > fecha_entrada)
) ENGINE=InnoDB;
 
-- ------------------------------------------------------------
-- 6. REGISTRO
--    Check-in y check-out real del huésped
-- ------------------------------------------------------------
CREATE TABLE registro (
  id_registro     INT             NOT NULL AUTO_INCREMENT,
  id_reserva      INT             NOT NULL UNIQUE,
  id_huesped      INT             NOT NULL,
  id_usuario      INT             NOT NULL,
  fecha_checkin   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_checkout  TIMESTAMP       NULL,
  deposito        DECIMAL(10, 2)  NOT NULL DEFAULT 0.00,
  notas           TEXT            NULL,
  PRIMARY KEY (id_registro),
  INDEX idx_reserva (id_reserva),
  INDEX idx_huesped (id_huesped),
  INDEX idx_usuario (id_usuario),
  CONSTRAINT fk_registro_reserva
    FOREIGN KEY (id_reserva)  REFERENCES reservas  (id_reserva)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_registro_huesped
    FOREIGN KEY (id_huesped)  REFERENCES huespedes (id_huesped)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_registro_usuario
    FOREIGN KEY (id_usuario)  REFERENCES usuarios  (id_usuario)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
 
-- ============================================================
--  DATOS DE EJEMPLO
-- ============================================================
 
-- Usuarios
INSERT INTO usuarios (nombre, email, password_hash, rol) VALUES
  ('Admin Principal',   'admin@buenavista.com',       '$2b$12$examplehash1', 'admin'),
  ('Carlos Recepcion',  'carlos@buenavista.com',      '$2b$12$examplehash2', 'recepcionista'),
  ('Maria Supervisora', 'maria@buenavista.com',       '$2b$12$examplehash3', 'supervisor');
 
-- Habitaciones
INSERT INTO habitaciones (numero, piso, tipo, capacidad, precio_noche, estado, descripcion) VALUES
  ('101', 1, 'sencilla',   1,  75.00, 'disponible', 'Habitación sencilla con vista al jardín'),
  ('102', 1, 'doble',      2,  95.00, 'disponible', 'Habitación doble con camas individuales'),
  ('201', 2, 'doble',      2, 110.00, 'disponible', 'Habitación doble con vista a la piscina'),
  ('202', 2, 'familiar',   4, 145.00, 'disponible', 'Habitación familiar con cama king y litera'),
  ('301', 3, 'suite',      2, 220.00, 'disponible', 'Suite con sala de estar y jacuzzi'),
  ('302', 3, 'presidencial', 4, 380.00, 'disponible', 'Suite presidencial con terraza privada');
 
-- Huéspedes
INSERT INTO huespedes (nombre, apellido, email, telefono, documento_identidad, tipo_documento, nacionalidad) VALUES
  ('Ana',   'Gómez',   'ana.gomez@email.com',   '+506 8888-1111', '1-0100-0001', 'cedula',    'Costa Rica'),
  ('Luis',  'Pérez',   'luis.perez@email.com',  '+506 8888-2222', '2-0200-0002', 'cedula',    'Costa Rica'),
  ('Sarah', 'Johnson', 'sarah.j@email.com',     '+1 555 0001',   'US123456',    'pasaporte', 'Estados Unidos');
 
-- Reservas
INSERT INTO reservas (id_huesped, id_habitacion, fecha_entrada, fecha_salida, num_huespedes, precio_total, estado) VALUES
  (1, 3, '2026-04-10', '2026-04-13', 2, 330.00, 'confirmada'),
  (2, 5, '2026-04-12', '2026-04-15', 2, 660.00, 'pendiente'),
  (3, 1, '2026-04-08', '2026-04-11', 1, 225.00, 'completada');
 
-- Inicio de sesión
INSERT INTO inicio_sesion (id_usuario, ip_address, token, estado) VALUES
  (1, '192.168.1.10', 'tok_admin_abc123',  'activa'),
  (2, '192.168.1.11', 'tok_carlos_xyz789', 'cerrada');
 
-- Registro (check-in completado para reserva 3)
INSERT INTO registro (id_reserva, id_huesped, id_usuario, fecha_checkin, fecha_checkout, deposito, notas) VALUES
  (3, 3, 2, '2026-04-08 14:30:00', '2026-04-11 11:00:00', 50.00, 'Huésped llegó puntual, sin incidentes.');
