CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_total_price ON orders(total_price);
CREATE INDEX idx_orders_created_at ON orders(created_at);
CREATE INDEX idx_orders_status_id ON orders(status_id);
CREATE INDEX idx_orders_payment_method_id ON orders(payment_method_id);

CREATE INDEX idx_order_status_history_order_id ON order_status_history(order_id);