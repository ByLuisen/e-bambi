CREATE TABLE IF NOT EXISTS payment_methods (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT pk_payment_methods PRIMARY KEY (id),
    CONSTRAINT uniq_payment_methods_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS orders (
    id UUID DEFAULT gen_random_uuid(),
    status_id UUID NOT NULL,
    payment_method_id UUID NOT NULL,
    user_id UUID NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    country VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    province VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    phone_number VARCHAR(25) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_orders PRIMARY KEY (id),
    CONSTRAINT fk_orders_payment_methods_payment_method_id FOREIGN KEY (payment_method_id) REFERENCES payment_methods (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_items (
    id UUID DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    sku VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    sold_by VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT fk_order_items_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_statuses (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(20) NOT NULL,
    CONSTRAINT pk_order_statuses PRIMARY KEY (id),
    CONSTRAINT uniq_order_statuses_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS order_status_history (
    id UUID DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL,
    order_status_id UUID NOT NULL,
    reason TEXT NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_order_status_history PRIMARY KEY (id),
    CONSTRAINT fk_order_status_history_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_status_history_order_statuses_order_status_id FOREIGN KEY (order_status_id) REFERENCES order_statuses (id) ON DELETE CASCADE
);

