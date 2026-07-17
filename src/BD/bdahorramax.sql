DROP DATABASE IF EXISTS bdahorramax;
CREATE DATABASE bdahorramax;
USE bdahorramax;

CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cargo VARCHAR(50) NOT NULL
);

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100) UNIQUE,
    clave VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    activo TINYINT NOT NULL DEFAULT 1,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id)
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(150) NOT NULL,
    id_categoria INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    activo TINYINT NOT NULL DEFAULT 1,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);

CREATE TABLE tipo_documento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_venta_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE detalle_venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES venta(id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES producto(id)
);

CREATE TABLE comprobante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_venta BIGINT NOT NULL UNIQUE,
    id_tipo_doc INT NOT NULL,
    num_doc VARCHAR(15) NOT NULL,
    nombre_cliente VARCHAR(150),
    tipo_pago ENUM('EFECTIVO','TARJETA','MIXTO') NOT NULL,
    monto_pagado DECIMAL(10,2) NOT NULL,
    vuelto DECIMAL(10,2) NOT NULL DEFAULT 0,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comprobante_venta FOREIGN KEY (id_venta) REFERENCES venta(id),
    CONSTRAINT fk_comprobante_tipodoc FOREIGN KEY (id_tipo_doc) REFERENCES tipo_documento(id)
);
 
-- =====================================================
-- DATOS DE PRUEBA
-- =====================================================

INSERT INTO rol (cargo) VALUES
('Administrador'),
('Cajero');

INSERT INTO usuario (dni, nombre, apellido, telefono, correo, clave, id_rol, activo) VALUES
('71234567', 'Carlos', 'Ramírez', '987654321', 'carlos.ramirez@ahorramax.com', '123456', 1, 1),
('72345678', 'María',  'Torres',  '987123456', 'maria.torres@ahorramax.com',  '123456', 2, 1),
('73456789', 'Jorge',  'Quispe',  '956123789', 'jorge.quispe@ahorramax.com',  '123456', 2, 1);

INSERT INTO categoria (nombre) VALUES
('Bebidas'),
('Abarrotes'),
('Lácteos'),
('Limpieza'),
('Snacks');

INSERT INTO producto (codigo, descripcion, id_categoria, precio, activo) VALUES
('P001', 'Coca Cola 500ml',        1, 3.50, 1),
('P002', 'Inca Kola 500ml',        1, 3.50, 1),
('P003', 'Agua San Luis 625ml',    1, 1.50, 1),
('P004', 'Arroz Costeño 1kg',      2, 5.20, 1),
('P005', 'Azúcar Rubia 1kg',       2, 4.80, 1),
('P006', 'Aceite Primor 1L',       2, 12.90, 1),
('P007', 'Leche Gloria 400g',      3, 4.50, 1),
('P008', 'Yogurt Laive 1L',        3, 7.00, 1),
('P009', 'Lejía Clorox 1L',        4, 5.50, 1),
('P010', 'Detergente Ariel 500g',  4, 6.90, 1),
('P011', 'Papas Lays 45g',         5, 2.50, 1),
('P012', 'Galletas Oreo 118g',     5, 3.20, 1);

INSERT INTO tipo_documento (nombre) VALUES
('DNI'),
('RUC'),
('Carnet de Extranjería');
 

INSERT INTO venta (id_usuario, fecha, total) VALUES
(2, '2026-07-10 09:15:00', 17.20);
 
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 3, 3.50, 10.50), 
(1, 4, 1, 5.20, 5.20),
(1, 3, 1, 1.50, 1.50);
 
INSERT INTO comprobante (id_venta, id_tipo_doc, num_doc, nombre_cliente, tipo_pago, monto_pagado, vuelto, fecha) VALUES
(1, 1, '45678912', 'Luis Fernández', 'EFECTIVO', 20.00, 2.80, '2026-07-10 09:15:30');
 
INSERT INTO venta (id_usuario, fecha, total) VALUES
(3, '2026-07-10 10:40:00', 20.90);
 
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES
(2, 6, 1, 12.90, 12.90),
(2, 9, 1, 5.50, 5.50),
(2, 11, 1, 2.50, 2.50);
 
INSERT INTO comprobante (id_venta, id_tipo_doc, num_doc, nombre_cliente, tipo_pago, monto_pagado, vuelto, fecha) VALUES
(2, 1, '78912345', 'Rosa Delgado', 'TARJETA', 20.90, 0.00, '2026-07-10 10:40:20');

INSERT INTO venta (id_usuario, fecha, total) VALUES
(2, '2026-07-11 16:05:00', 15.40);
 
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES
(3, 7, 2, 4.50, 9.00),
(3, 12, 2, 3.20, 6.40);
 
INSERT INTO comprobante (id_venta, id_tipo_doc, num_doc, nombre_cliente, tipo_pago, monto_pagado, vuelto, fecha) VALUES
(3, 1, '12345678', 'Pedro Alva', 'MIXTO', 15.40, 0.00, '2026-07-11 16:05:15');