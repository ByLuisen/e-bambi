-- Función de trigger para actualizar las columnas de fecha
CREATE OR REPLACE FUNCTION fn_update_timestamp_column()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        -- Asigna el valor a la columna correcta, según la tabla
        IF TG_TABLE_NAME = 'orders' THEN
            NEW.updated_at = CURRENT_TIMESTAMP;
        ELSIF TG_TABLE_NAME = 'order_items' THEN
            NEW.created_at = CURRENT_TIMESTAMP;
        ELSIF TG_TABLE_NAME = 'order_status_history' THEN
            NEW.changed_at = CURRENT_TIMESTAMP;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers para las tablas correspondientes

CREATE TRIGGER trg_set_timestamp_orders
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp_column();

CREATE TRIGGER trg_set_timestamp_order_items
BEFORE UPDATE ON order_items
FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp_column();

CREATE TRIGGER trg_set_timestamp_order_status_history
BEFORE UPDATE ON order_status_history
FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp_column();
