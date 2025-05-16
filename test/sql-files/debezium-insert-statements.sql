INSERT INTO order_outbox_commands(id, aggregatetype, aggregateid, event_type, saga_status, payload)
VALUES(gen_random_uuid(), 'payment.validate', gen_random_uuid(), 'OrderProcessingSaga', 'STARTED', '{
  "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
  "paymentMethodId": "d215b5f8-0249-4dc5-89a3-51fd148cfb41"
}');

INSERT INTO payment_outbox_events(id, aggregatetype, aggregateid, event_type, payload)
VALUES(gen_random_uuid(), 'processed', gen_random_uuid(), 'OrderProcessingSaga', '{
  "orderId": "632a7ff9-f239-4cc6-bed3-adadffadc6c0",
  "paymentMethodId": "d215b5f8-0249-4dc5-89a3-51fd148cfb41",
}');