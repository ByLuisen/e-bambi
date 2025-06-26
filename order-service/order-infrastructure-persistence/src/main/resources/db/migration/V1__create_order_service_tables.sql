CREATE TYPE type_order_status
AS ENUM ('PENDING', 'PRODUCTS_RESERVED', 'CREATED', 'CANCELLED', 'CANCELLING');

CREATE TABLE IF NOT EXISTS orders (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    order_status type_order_status NOT NULL,
    payment_method_id UUID NOT NULL,
    payment_method VARCHAR(50),
    country VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    province VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    phone_number VARCHAR(25) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    failure_messages VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_items (
    id UUID NOT NULL,
    order_id UUID NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    supplier_id UUID,
    supplier VARCHAR(100) NOT NULL,
    product_id UUID NOT NULL,
    sku VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT fk_order_items_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_status_history (
    id UUID NOT NULL,
    order_id UUID NOT NULL,
    order_status type_order_status NOT NULL,
    reason TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT pk_order_status_history PRIMARY KEY (id),
    CONSTRAINT fk_order_status_history_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT uniq_order_status_history_order_id_order_status UNIQUE (order_id, order_status)
);

CREATE TABLE IF NOT EXISTS order_outbox_events (
  id UUID NOT NULL,
  aggregatetype VARCHAR(255) NOT NULL,
  aggregateid VARCHAR(255) NOT NULL,
  event_type VARCHAR(255),
  saga_status VARCHAR(255),
  payload JSONB,
  CONSTRAINT pk_order_outbox_events PRIMARY KEY (id),
  CONSTRAINT uniq_order_outbox_events_aggregatetype_aggregateid UNIQUE (aggregatetype, aggregateid)
);