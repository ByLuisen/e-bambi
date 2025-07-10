CREATE INDEX idx_images_product_id ON images(product_id);

CREATE INDEX idx_inventory_movements_supplier_id_movement_type_id ON inventory_movements(supplier_id, movement_type_id);
CREATE INDEX idx_inventory_movements_supplier_id ON inventory_movements(supplier_id);
CREATE INDEX idx_inventory_movements_product_id ON inventory_movements(product_id);
CREATE INDEX idx_inventory_movements_product_sku ON inventory_movements(product_sku);

CREATE INDEX idx_offers_supplier_id ON offers(supplier_id);
CREATE INDEX idx_offers_product_id ON offers(product_id);
CREATE INDEX idx_offers_supplier_id_product_id ON offers(supplier_id, product_id);

CREATE INDEX idx_inventory_outbox_events_aggregateid_aggregatetype ON inventory_outbox_events(aggregateid, aggregatetype);

CREATE INDEX idx_products_sku ON products(sku);
CREATE INDEX idx_products_brand_id ON products(brand_id);
CREATE INDEX idx_products_department_id ON products(department_id);
CREATE INDEX idx_products_product_status_id ON products(product_status_id);
CREATE INDEX idx_products_brand_id_department_id_product_status_id ON products(brand_id, department_id, product_status_id);