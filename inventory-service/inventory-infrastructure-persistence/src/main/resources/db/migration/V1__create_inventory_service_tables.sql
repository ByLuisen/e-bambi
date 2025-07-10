CREATE TABLE IF NOT EXISTS brands (
    id UUID,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_brands PRIMARY KEY (id),
    CONSTRAINT uniq_brands_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS departments (
    id UUID,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_departments PRIMARY KEY (id),
    CONSTRAINT uniq_departments_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS product_statuses (
    id UUID,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT pk_product_statuses PRIMARY KEY (id),
    CONSTRAINT uniq_product_statuses_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id UUID,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_suppliers PRIMARY KEY (id),
    CONSTRAINT uniq_suppliers_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS products (
    id UUID,
    brand_id UUID NOT NULL,
    department_id UUID NOT NULL,
    product_status_id UUID NOT NULL,
    sku VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ,
    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT fk_products_brands_brand_id FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE CASCADE,
    CONSTRAINT fk_products_departments_department_id FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE,
    CONSTRAINT fk_products_product_statuses_product_status_id FOREIGN KEY (product_status_id) REFERENCES product_statuses(id) ON DELETE CASCADE,
    CONSTRAINT uniq_products_sku UNIQUE (sku)
);

CREATE TABLE IF NOT EXISTS offers (
    id UUID,
    supplier_id UUID NOT NULL,
    product_id UUID NOT NULL,
    stock INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    version INTEGER NOT NULL,
    CONSTRAINT pk_offers PRIMARY KEY (id),
    CONSTRAINT uniq_offers_supplier_id_product_id UNIQUE (supplier_id, product_id),
    CONSTRAINT fk_offers_suppliers_supplier_id FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE,
    CONSTRAINT fk_offers_products_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS images (
    id UUID,
    product_id UUID NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    CONSTRAINT pk_images PRIMARY KEY (id),
    CONSTRAINT fk_images_products_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT uniq_images_image_url UNIQUE (image_url)
);

CREATE TABLE IF NOT EXISTS movement_types (
    id UUID,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT pk_movement_types PRIMARY KEY (id),
    CONSTRAINT uniq_movement_types_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS inventory_movements (
    id UUID NOT NULL,
    supplier_id UUID NOT NULL,
    product_id UUID NOT NULL,
    movement_type_id UUID NOT NULL,
    product_sku VARCHAR(100) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    previous_stock INT NOT NULL,
    new_stock INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT pk_inventory_movements PRIMARY KEY (id),
    CONSTRAINT fk_inventory_movements_suppliers_supplier_id FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE,
    CONSTRAINT fk_inventory_movements_products_product_id FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_inventory_movements_movements_movement_type_id FOREIGN KEY (movement_type_id) REFERENCES movement_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS inventory_outbox_events (
    id UUID NOT NULL,
    aggregatetype VARCHAR (255) NOT NULL,
    aggregateid VARCHAR (255) NOT NULL,
    event_type VARCHAR (255),
    payload VARCHAR (255),
    CONSTRAINT pk_inventory_outbox_events PRIMARY KEY (id),
    CONSTRAINT uniq_inventory_outbox_events_aggregatetype_aggregateid UNIQUE (aggregatetype, aggregateid)
);