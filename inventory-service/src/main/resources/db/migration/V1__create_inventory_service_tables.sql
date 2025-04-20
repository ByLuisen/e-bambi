CREATE TABLE IF NOT EXISTS brands (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_brands PRIMARY KEY (id),
    CONSTRAINT uniq_brands_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS departments (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_departments PRIMARY KEY (id),
    CONSTRAINT uniq_departments_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS product_statuses (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(30) NOT NULL,
    CONSTRAINT pk_product_statuses PRIMARY KEY (id),
    CONSTRAINT uniq_product_statuses_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS products (
    id UUID DEFAULT gen_random_uuid(),
    brand_id UUID NOT NULL,
    department_id UUID NOT NULL,
    product_status_id UUID NOT NULL,
    sku VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    version_number INT DEFAULT 0,
    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT uniq_products_sku UNIQUE (sku),
    CONSTRAINT fk_products_brands_brand_id FOREIGN KEY (brand_id) REFERENCES brands (id) ON DELETE CASCADE,
    CONSTRAINT fk_products_departments_department_id FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE,
    CONSTRAINT fk_products_product_statuses_product_status_id FOREIGN KEY (product_status_id) REFERENCES product_statuses (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS images (
    id UUID DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    url_image VARCHAR(255) NOT NULL,
    CONSTRAINT pk_images PRIMARY KEY (id),
    CONSTRAINT uniq_images_url_image UNIQUE (url_image),
    CONSTRAINT fk_images_products_product_id FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS suppliers (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_suppliers PRIMARY KEY (id),
    CONSTRAINT uniq_suppliers_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS product_supplier (
    id UUID DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    supplier_id UUID NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    CONSTRAINT pk_product_supplier PRIMARY KEY (id),
    CONSTRAINT fk_product_supplier_products_product_id FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_supplier_suppliers_supplier_id FOREIGN KEY (supplier_id) REFERENCES suppliers (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS movement_types (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT pk_movement_types PRIMARY KEY (id),
    CONSTRAINT uniq_movement_types_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS inventory_movements (
    id UUID DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    movement_type_id UUID NOT NULL,
    product_sku VARCHAR(100) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    previous_stock INT,
    new_stock INT,
    reference_id UUID NOT NULL,
    reference_table VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_inventory_movements PRIMARY KEY (id),
    CONSTRAINT fk_inventory_movements_products_product_id FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT fk_inventory_movements_movement_types_movement_type_id FOREIGN KEY (movement_type_id) REFERENCES movement_types (id) ON DELETE CASCADE
);