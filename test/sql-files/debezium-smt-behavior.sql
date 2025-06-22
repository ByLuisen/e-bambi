BEGIN TRANSACTION;

INSERT INTO order_outbox_events(id, aggregatetype, aggregateid, event_type, saga_status, payload)
VALUES('25694e6f-2a41-4bf3-bd8f-a5a84a21405d', 'payment.validate', gen_random_uuid(), 'OrderProcessingSaga', 'STARTED', '{
  "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
  "paymentMethodId": "d215b5f8-0249-4dc5-89a3-51fd148cfb41"
}');

DELETE FROM order_outbox_commands WHERE id = '25694e6f-2a41-4bf3-bd8f-a5a84a21405d';

COMMIT;