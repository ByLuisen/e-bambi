INSERT INTO brands (name) VALUES
('Nike'),
('Adidas'),
('Samsung'),
('Apple'),
('Sony'),
('LG'),
('Dell'),
('Under Armour'),
('Canon'),
('HP'),
('Bose'),
('GoPro'),
('Microsoft'),
('Lenovo'),
('Garmin'),
('Razer'),
('Asus'),
('Fitbit'),
('DJI');

INSERT INTO departments (name) VALUES
('Electronics'),
('Clothing'),
('Accessories'),
('Home Appliances'),
('Sports');

INSERT INTO product_statuses (id, name) VALUES
('ba0a6601-dfe4-485a-83f9-c149db939f2e', 'Inactive'),
('1e8b7e83-bf45-4838-80bd-aa9e2cb9c286', 'Active');

INSERT INTO product_statuses (name) VALUES
('Out of Stock'),
('Pre-order'),
('On Offer');

INSERT INTO products (brand_id, department_id, product_status_id, sku, name, price, description) VALUES
((SELECT id FROM brands WHERE name = 'Samsung'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SAM-001-EL', 'Samsung Galaxy S23', 799.99, 'Último modelo de la serie Galaxy con pantalla AMOLED de 120Hz.'),
((SELECT id FROM brands WHERE name = 'Adidas'), (SELECT id FROM departments WHERE name = 'Sports'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'AD-002-SP', 'Adidas Ultraboost', 149.99, 'Zapatillas de running con tecnología Boost para máxima comodidad.'),
((SELECT id FROM brands WHERE name = 'Sony'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SONY-003-EL', 'Sony PlayStation 5', 499.99, 'Consola de videojuegos de última generación con 4K y 120Hz.'),
((SELECT id FROM brands WHERE name = 'Samsung'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SAM-004-EL', 'Samsung Galaxy Watch', 249.99, 'Reloj inteligente con monitorización de salud avanzada.'),
((SELECT id FROM brands WHERE name = 'Nike'), (SELECT id FROM departments WHERE name = 'Clothing'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'NIKE-005-SP', 'Nike Air Max 270', 129.99, 'Zapatillas deportivas cómodas y con gran estilo.'),
((SELECT id FROM brands WHERE name = 'Apple'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Out of Stock'), 'APPL-006-AC', 'Apple Watch Series 8', 399.99, 'Reloj inteligente con monitorización avanzada de salud y deporte.'),
((SELECT id FROM brands WHERE name = 'LG'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'LG-007-EL', 'LG OLED TV', 1799.99, 'Televisor OLED 4K de 65" con HDR y Dolby Vision.'),
((SELECT id FROM brands WHERE name = 'Dell'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'DELL-008-EL', 'Dell XPS 13', 999.99, 'Laptop ultradelgada con pantalla InfinityEdge de 13 pulgadas.'),
((SELECT id FROM brands WHERE name = 'Under Armour'), (SELECT id FROM departments WHERE name = 'Clothing'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'UA-009-CL', 'Under Armour Compression Shirt', 49.99, 'Camiseta de compresión para rendimiento deportivo.'),
((SELECT id FROM brands WHERE name = 'Sony'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SONY-010-AC', 'Sony Noise Cancelling Headphones', 299.99, 'Auriculares con cancelación de ruido avanzada.'),
((SELECT id FROM brands WHERE name = 'Canon'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'CANON-011-EL', 'Canon EOS 80D', 1199.99, 'Cámara réflex digital de 24.2 megapíxeles con lente de 18-55mm.'),
((SELECT id FROM brands WHERE name = 'Adidas'), (SELECT id FROM departments WHERE name = 'Clothing'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'AD-012-CL', 'Adidas Superstars', 89.99, 'Zapatillas clásicas de Adidas con diseño en blanco y negro.'),
((SELECT id FROM brands WHERE name = 'Nike'), (SELECT id FROM departments WHERE name = 'Clothing'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'NIKE-013-CL', 'Nike Air Force 1', 99.99, 'Zapatillas de cuero clásico, ideales para el uso diario.'),
((SELECT id FROM brands WHERE name = 'LG'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'LG-014-EL', 'LG Bluetooth Speaker', 129.99, 'Altavoz bluetooth portátil con sonido estéreo de alta calidad.'),
((SELECT id FROM brands WHERE name = 'Apple'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'APPL-015-EL', 'Apple iPhone 14', 999.99, 'Smartphone de última generación con 5G y cámara avanzada.'),
((SELECT id FROM brands WHERE name = 'Sony'), (SELECT id FROM departments WHERE name = 'Home Appliances'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SONY-016-EL', 'Sony 4K Blu-ray Player', 249.99, 'Reproductor Blu-ray con soporte para 4K UHD y Dolby Atmos.'),
((SELECT id FROM brands WHERE name = 'Under Armour'), (SELECT id FROM departments WHERE name = 'Clothing'), (SELECT id FROM product_statuses WHERE name = 'Out of Stock'), 'UA-017-SP', 'Under Armour Running Shoes', 120.99, 'Zapatillas para running con excelente amortiguación.'),
((SELECT id FROM brands WHERE name = 'Apple'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'APPL-018-EL', 'Apple iPad Pro 12.9"', 1099.99, 'Tablet de 12.9 pulgadas con procesador M1 y 120Hz.'),
((SELECT id FROM brands WHERE name = 'Samsung'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SAM-019-EL', 'Samsung 32" Curved Monitor', 299.99, 'Monitor curvado de 32 pulgadas con resolución 1440p.'),
((SELECT id FROM brands WHERE name = 'Canon'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'CANON-020-EL', 'Canon PowerShot SX740 HS', 399.99, 'Cámara compacta con zoom de 40x y grabación en 4K.'),
((SELECT id FROM brands WHERE name = 'HP'), (SELECT id FROM departments WHERE name = 'Home Appliances'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'HP-021-EL', 'HP OfficeJet Pro 9015', 229.99, 'Impresora multifunción con capacidad para imprimir hasta 22 ppm.'),
((SELECT id FROM brands WHERE name = 'Samsung'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SAM-050-EL', 'Samsung Galaxy Tab S7', 649.99, 'Tablet con pantalla Super AMOLED de 11 pulgadas y S Pen incluido.'),
((SELECT id FROM brands WHERE name = 'Bose'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'BOSE-022-EL', 'Bose QuietComfort 45', 329.99, 'Auriculares inalámbricos con cancelación de ruido líder en la industria.'),
((SELECT id FROM brands WHERE name = 'GoPro'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'GOPRO-023-EL', 'GoPro Hero 10 Black', 499.99, 'Cámara de acción con estabilización avanzada y grabación en 5.3K.'),
((SELECT id FROM brands WHERE name = 'Microsoft'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'MSFT-024-EL', 'Microsoft Surface Pro 9', 1099.99, 'Laptop-tablet híbrida con procesador Intel y pantalla táctil de 13".'),
((SELECT id FROM brands WHERE name = 'Lenovo'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'LEN-025-EL', 'Lenovo ThinkPad X1 Carbon', 1299.99, 'Ultrabook premium con diseño ligero y batería de larga duración.'),
((SELECT id FROM brands WHERE name = 'Garmin'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'GAR-026-AC', 'Garmin Fenix 7', 799.99, 'Reloj inteligente con GPS avanzado y funciones de entrenamiento.'),
((SELECT id FROM brands WHERE name = 'Razer'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'RAZER-027-EL', 'Razer Blade 15', 1799.99, 'Laptop gamer con pantalla de 165Hz y GPU NVIDIA RTX 3070.'),
((SELECT id FROM brands WHERE name = 'Asus'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'ASUS-028-EL', 'Asus ROG Strix G17', 1599.99, 'Laptop gamer con procesador AMD Ryzen 9 y RTX 3080.'),
((SELECT id FROM brands WHERE name = 'Fitbit'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'FIT-029-AC', 'Fitbit Charge 5', 149.99, 'Pulsera de actividad con GPS y monitor de frecuencia cardíaca.'),
((SELECT id FROM brands WHERE name = 'Samsung'), (SELECT id FROM departments WHERE name = 'Home Appliances'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SAM-030-HA', 'Samsung Smart Refrigerator', 1999.99, 'Refrigerador inteligente con pantalla táctil y control por voz.'),
((SELECT id FROM brands WHERE name = 'Sony'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'SONY-031-EL', 'Sony Alpha 7 IV', 2499.99, 'Cámara sin espejo con sensor full-frame de 33 MP y video 4K.'),
((SELECT id FROM brands WHERE name = 'Apple'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'APPL-032-AC', 'Apple AirPods Pro 2', 249.99, 'Auriculares inalámbricos con cancelación de ruido activa.'),
((SELECT id FROM brands WHERE name = 'DJI'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'DJI-033-EL', 'DJI Mavic Air 2', 799.99, 'Drone con cámara 4K, detección de obstáculos y vuelo autónomo.'),
((SELECT id FROM brands WHERE name = 'HP'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'HP-034-EL', 'HP Spectre x360', 1399.99, 'Laptop convertible con pantalla OLED 4K y lápiz digital.'),
((SELECT id FROM brands WHERE name = 'Nike'), (SELECT id FROM departments WHERE name = 'Clothing'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'NIKE-035-CL', 'Nike Vaporfly Next%', 249.99, 'Zapatillas de running de alto rendimiento con tecnología ZoomX.'),
((SELECT id FROM brands WHERE name = 'Canon'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'CANON-036-AC', 'Canon RF 50mm f/1.2L', 2299.99, 'Lente de alto rendimiento para fotografía profesional.'),
((SELECT id FROM brands WHERE name = 'Samsung'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Out of Stock'), 'SAM-037-EL', 'Samsung Galaxy Z Fold 4', 1799.99, 'Smartphone plegable con pantalla AMOLED de 120Hz.'),
((SELECT id FROM brands WHERE name = 'Dell'), (SELECT id FROM departments WHERE name = 'Electronics'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'DELL-038-EL', 'Dell Alienware m15', 1899.99, 'Laptop gamer con pantalla de 360Hz y procesador Intel i9.'),
((SELECT id FROM brands WHERE name = 'Garmin'), (SELECT id FROM departments WHERE name = 'Accessories'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'GAR-039-AC', 'Garmin Edge 530', 299.99, 'Ciclocomputador con GPS y métricas avanzadas de rendimiento.'),
((SELECT id FROM brands WHERE name = 'LG'), (SELECT id FROM departments WHERE name = 'Home Appliances'), (SELECT id FROM product_statuses WHERE name = 'Active'), 'LG-040-HA', 'LG Washing Machine AI DD', 799.99, 'Lavadora inteligente con detección de carga y optimización de lavado.');

INSERT INTO images (product_id, url_image) VALUES
((SELECT id FROM products WHERE sku = 'SAM-001-EL'), ('https://example.com/images/galaxy_s23.jpg')),
((SELECT id FROM products WHERE sku = 'AD-002-SP'), ('https://example.com/images/ultraboost.jpg')),
((SELECT id FROM products WHERE sku = 'SONY-003-EL'), ('https://example.com/images/ps5.jpg')),
((SELECT id FROM products WHERE sku = 'NIKE-005-SP'), ('https://example.com/images/air_max_270.jpg')),
((SELECT id FROM products WHERE sku = 'APPL-006-AC'), ('https://example.com/images/apple_watch.jpg')),
((SELECT id FROM products WHERE sku = 'LG-007-EL'), ('https://example.com/images/oled_tv.jpg')),
((SELECT id FROM products WHERE sku = 'DELL-008-EL'), ('https://example.com/images/xps_13.jpg')),
((SELECT id FROM products WHERE sku = 'SAM-050-EL'), ('https://example.com/images/galaxy_tab_s7.jpg'));

INSERT INTO suppliers (name) VALUES
('BestTech Distributors'),
('Global Apparel Co.'),
('PlayTech Supplies'),
('Footwear Express'),
('TechGear Partners'),
('SmartHome Electronics'),
('Wireless World Inc.'),
('Digital Media Distributors'),
('Pro Sports Equipment'),
('Optical Imaging Co.'),
('GadgetBox Supplies'),
('Gaming Hardware LLC'),
('Luxury Fashion Group'),
('Outdoor Gear Suppliers'),
('Techtronics Co.'),
('SportsLife USA'),
('Global Electronics Distribution'),
('Photography Supplies International'),
('MediaTech Retailers'),
('Apparel World Ltd.');

INSERT INTO product_supplier (product_id, supplier_id, price, stock) VALUES
((SELECT id FROM products WHERE sku = 'SAM-001-EL'), (SELECT id FROM suppliers WHERE name = 'BestTech Distributors'), 799.99, 150),
((SELECT id FROM products WHERE sku = 'AD-002-SP'), (SELECT id FROM suppliers WHERE name = 'Global Apparel Co.'), 149.99, 200),
((SELECT id FROM products WHERE sku = 'SONY-003-EL'), (SELECT id FROM suppliers WHERE name = 'PlayTech Supplies'), 499.99, 100),
((SELECT id FROM products WHERE sku = 'SAM-004-EL'), (SELECT id FROM suppliers WHERE name = 'BestTech Distributors'), 249.99, 120),
((SELECT id FROM products WHERE sku = 'NIKE-005-SP'), (SELECT id FROM suppliers WHERE name = 'Global Apparel Co.'), 129.99, 300),
((SELECT id FROM products WHERE sku = 'APPL-006-AC'), (SELECT id FROM suppliers WHERE name = 'PlayTech Supplies'), 399.99, 80),
((SELECT id FROM products WHERE sku = 'LG-007-EL'), (SELECT id FROM suppliers WHERE name = 'Footwear Express'), 1799.99, 50),
((SELECT id FROM products WHERE sku = 'DELL-008-EL'), (SELECT id FROM suppliers WHERE name = 'TechGear Partners'), 999.99, 60),
((SELECT id FROM products WHERE sku = 'UA-009-CL'), (SELECT id FROM suppliers WHERE name = 'SmartHome Electronics'), 49.99, 500),
((SELECT id FROM products WHERE sku = 'SONY-010-AC'), (SELECT id FROM suppliers WHERE name = 'Wireless World Inc.'), 299.99, 150),
((SELECT id FROM products WHERE sku = 'CANON-011-EL'), (SELECT id FROM suppliers WHERE name = 'Digital Media Distributors'), 1199.99, 70),
((SELECT id FROM products WHERE sku = 'AD-012-CL'), (SELECT id FROM suppliers WHERE name = 'Pro Sports Equipment'), 89.99, 250),
((SELECT id FROM products WHERE sku = 'NIKE-013-CL'), (SELECT id FROM suppliers WHERE name = 'Optical Imaging Co.'), 99.99, 500),
((SELECT id FROM products WHERE sku = 'SAM-050-EL'), (SELECT id FROM suppliers WHERE name = 'Apparel World Ltd.'), 649.99, 90);

INSERT INTO movement_types (name, description) VALUES
('Sale', 'Movimiento en el que un producto es vendido a un cliente.'),
('Restock', 'Cuando un producto es reabastecido en el inventario, ya sea porque se realizó un pedido a un proveedor o por cualquier otra razón.'),
('Customer Return', 'Cuando un cliente devuelve un producto que había comprado previamente.'),
('Supplier Return', 'Si el negocio devuelve productos al proveedor por alguna razón (defectuoso, exceso, etc.).'),
('Inventory Adjustment', 'Cuando se realiza un ajuste manual en el inventario, ya sea por errores de conteo o cambios no registrados.'),
('Warehouse Transfer', 'Si el inventario se mueve de un almacén a otro, disminuyendo el inventario, disminuyendo el inventario en un almacén o aumentándolo en otro.'),
('Destruction', 'Cuando un producto es destruido, ya sea por estar defectuoso o por caducidad.'),
('Damaged Goods', 'Cuando se detecta que un producto ha sido dañado y ya no es apto para la venta.');

INSERT INTO inventory_movements (product_id, movement_type_id, product_sku, product_name, quantity, previous_stock, new_stock, reference_id, reference_table) VALUES
((SELECT id FROM products WHERE sku = 'SAM-001-EL'), (SELECT id FROM movement_types WHERE name = 'Restock'), 'SAM-001-EL', 'Samsung Galaxy S23', 20, 150, 170, 'dd08b471-d446-4ba0-88c9-4cc4ae5d945a', 'internal_orders'),
((SELECT id FROM products WHERE sku = 'AD-002-SP'), (SELECT id FROM movement_types WHERE name = 'Restock'),'AD-002-SP', 'Adidas Ultraboost', 30, 200, 230, '378743bb-e9b8-4b2f-9f8b-a9933ecaa862', 'internal_orders'),
((SELECT id FROM products WHERE sku = 'SONY-003-EL'), (SELECT id FROM movement_types WHERE name = 'Sale'), 'SONY-003-EL', 'Sony PlayStation 5', 10, 100, 90, 'aa668bed-1329-4c45-8650-100ea33abf50', 'customer_orders'),
((SELECT id FROM products WHERE sku = 'NIKE-005-SP'), (SELECT id FROM movement_types WHERE name = 'Customer Return'), 'NIKE-005-SP', 'Nike Air Max 270', 5, 300, 295, '06314f33-cc6b-4cc6-b16a-6e39c288a79d', 'customer_orders'),
((SELECT id FROM products WHERE sku = 'APPL-006-AC'), (SELECT id FROM movement_types WHERE name = 'Inventory Adjustment'), 'APPL-006-AC', 'Apple Watch Series 8', 15, 80, 95, 'dd08b471-d446-4ba0-88c9-4cc4ae5d945a', 'internal_orders'),
((SELECT id FROM products WHERE sku = 'SAM-050-EL'), (SELECT id FROM movement_types WHERE name = 'Warehouse Transfer'), 'SAM-050-EL', 'Samsung Galaxy Tab S7', 10, 90, 100, 'aa668bed-1329-4c45-8650-100ea33abf50', 'internal_orders');
