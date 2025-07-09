-- Script de inicialización de datos para la base de datos H2
-- Este script se ejecuta automáticamente al iniciar la aplicación

-- Insertar productos de ejemplo
INSERT INTO products (name, description, price, quantity, created_at, updated_at) VALUES
('Laptop Dell XPS 13', 'Laptop ultrabook con procesador Intel i7, 16GB RAM, SSD 512GB', 999.99, 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('iPhone 14 Pro', 'Smartphone Apple con cámara Pro, 128GB, color Space Black', 1099.00, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Monitor Samsung 4K', 'Monitor 27 pulgadas 4K UHD, panel IPS, conexión USB-C', 449.99, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Teclado Mecánico Logitech', 'Teclado mecánico RGB con switches Cherry MX Blue', 129.99, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mouse Inalámbrico Microsoft', 'Mouse ergonómico inalámbrico con precisión óptica', 39.99, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Auriculares Sony WH-1000XM4', 'Auriculares con cancelación de ruido activa, Bluetooth 5.0', 349.99, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tablet iPad Air', 'Tablet Apple 10.9 pulgadas, 64GB, WiFi + Cellular', 599.99, 35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SSD Samsung 1TB', 'Disco sólido interno SATA 2.5, velocidad de lectura 550MB/s', 89.99, 40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Webcam Logitech C920', 'Cámara web HD 1080p con micrófono incorporado', 79.99, 60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Router WiFi 6 ASUS', 'Router inalámbrico AC3000 con tecnología WiFi 6, 4 antenas', 199.99, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Impresora HP LaserJet', 'Impresora láser monocromática, velocidad 22 ppm', 159.99, 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Disco Duro Externo 2TB', 'Disco duro portátil USB 3.0, compatible con PC y Mac', 79.99, 45, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cargador Inalámbrico', 'Base de carga inalámbrica Qi compatible con iPhone y Android', 29.99, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cable USB-C', 'Cable USB-C a USB-C, 1.5 metros, carga rápida', 19.99, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hub USB 3.0', 'Hub de 4 puertos USB 3.0 con alimentación externa', 24.99, 55, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Comentarios sobre los datos insertados:
-- - Se incluyen productos variados de tecnología
-- - Precios realistas en USD
-- - Cantidades variadas para simular diferentes niveles de stock
-- - Algunos productos tienen stock bajo (< 20 unidades) para probar funcionalidades
-- - Timestamps automáticos para created_at y updated_at