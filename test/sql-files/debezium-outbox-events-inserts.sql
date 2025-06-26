INSERT INTO order_outbox_events(id, aggregatetype, aggregateid, event_type, saga_status, payload)
VALUES(gen_random_uuid(), 'inventory.reserve', gen_random_uuid(), 'OrderProcessingSaga', 'STARTED', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
    "products": [
        {
            "id": "839546df-e9ab-45c4-b5e0-06f10ca5c3d7",
            "supplierId": "fcb9a876-fe8a-4166-a22b-e37c95da0d49",
            "quantity": 1,
            "price": 799.99
        },
        {
            "id": "1c6e3e3c-7994-4b16-8b6d-3da13e2fb1f2",
            "supplierId": "fcb9a876-fe8a-4166-a22b-e37c95da0d49",
            "quantity": 2,
            "price": 249.99
        }
    ]
}');

INSERT INTO order_outbox_events(id, aggregatetype, aggregateid, event_type, saga_status, payload)
VALUES(gen_random_uuid(), 'payment.validate', gen_random_uuid(), 'OrderProcessingSaga', 'PROCESSING', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
    "paymentMethodId": "13794ded-ddb6-4c82-813c-788cee1c7027"
}');


INSERT INTO order_outbox_events(id, aggregatetype, aggregateid, event_type, saga_status, payload)
VALUES (gen_random_uuid(), 'inventory.cancel_reservation', gen_random_uuid(), 'OrderProcessingSaga', 'COMPENSATING', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
    "products": [
        {
            "id": "839546df-e9ab-45c4-b5e0-06f10ca5c3d7",
            "supplierId": "fcb9a876-fe8a-4166-a22b-e37c95da0d49",
            "quantity": 1
        },
        {
            "id": "1c6e3e3c-7994-4b16-8b6d-3da13e2fb1f2",
            "supplierId": "fcb9a876-fe8a-4166-a22b-e37c95da0d49",
            "quantity": 2
        }
    ]
}')

INSERT INTO inventory_outbox_events(id, aggregatetype, aggregateid, event_type, payload)
VALUES (gen_random_uuid(), 'reserved', gen_random_uuid(), 'OrderProcessingSaga', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d"
}')

INSERT INTO inventory_outbox_events(id, aggregatetype, aggregateid, event_type, payload)
VALUES (gen_random_uuid(), 'reservation_failed', gen_random_uuid(), 'OrderProcessingSaga', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
    "failureMessages": [
        "Insufficient stock for th  e product with id: 839546df-e9ab-45c4-b5e0-06f10ca5c3d7.",
        "Product with id: 4ccf4049-ebc3-4ef5-933c-1ab37abb4371 could not be found."
    ]
}')

INSERT INTO inventory_outbox_events(id, aggregatetype, aggregateid, event_type, payload)
VALUES (gen_random_uuid(), 'reservation_cancelled', gen_random_uuid(), 'OrderProcessingSaga', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d"
}')

INSERT INTO payment_outbox_events(id, aggregatetype, aggregateid, event_type, payload)
VALUES (gen_random_uuid, 'validated', gen_random_uuid(), 'OrderProcessingSaga', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
}');

INSERT INTO payment_outbox_events(id, aggregatetype, aggregateid, event_type, payload)
VALUES (gen_random_uuid, 'validation_failed', gen_random_uuid(), 'OrderProcessingSaga', '{
    "orderId": "25694e6f-2a41-4bf3-bd8f-a5a84a21405d",
    "failureMessages": [
        "The provided payment method ID doesn't exist"
    ]
}');