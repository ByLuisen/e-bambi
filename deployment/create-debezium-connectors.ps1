$headers = New-Object "System.Collections.Generic.Dictionary[[String],[String]]"
$headers.Add("Content-Type", "application/json")

$body = @"
{
  `"name`": `"debezium-postgres-order-events`",
  `"config`": {
    `"connector.class`": `"io.debezium.connector.postgresql.PostgresConnector`",
    `"tasks.max`": `"1`",
    `"database.hostname`": `"order-service-db`",
    `"database.port`": `"5432`",
    `"database.user`": `"user`",
    `"database.password`": `"password`",
    `"database.dbname`" : `"order-service`",
    `"database.server.name`": `"PostgreSQL-14`",
    `"topic.prefix`": `"debezium`",
    `"table.include.list`": `"public.order_outbox_events`",
    `"tombstones.on.delete`" : `"false`",
    `"slot.name`" : `"order_outbox_events_slot`",
    `"plugin.name`": `"pgoutput`",
    `"transforms`": `"outbox`",
    `"transforms.outbox.type`": `"io.debezium.transforms.outbox.EventRouter`",
    `"transforms.outbox.route.topic.replacement`": `"order.`${routedByValue}`",
    `"transforms.outbox.table.fields.additional.placement`": `"event_type:header,saga_status:header`"
  }
}
"@

$response = Invoke-RestMethod 'http://localhost:8083/connectors' -Method 'POST' -Headers $headers -Body $body
$response | ConvertTo-Json

$headers = New-Object "System.Collections.Generic.Dictionary[[String],[String]]"
$headers.Add("Content-Type", "application/json")

$body = @"
{
  `"name`": `"debezium-postgres-payment-events`",
  `"config`": {
    `"connector.class`": `"io.debezium.connector.postgresql.PostgresConnector`",
    `"tasks.max`": `"1`",
    `"database.hostname`": `"payment-service-db`",
    `"database.port`": `"5432`",
    `"database.user`": `"user`",
    `"database.password`": `"password`",
    `"database.dbname`" : `"payment-service`",
    `"database.server.name`": `"PostgreSQL-14`",
    `"topic.prefix`": `"debezium`",
    `"table.include.list`": `"public.payment_outbox_events`",
    `"tombstones.on.delete`" : `"false`",
    `"slot.name`" : `"payment_outbox_events_slot`",
    `"plugin.name`": `"pgoutput`",
    `"transforms`": `"outbox`",
    `"transforms.outbox.type`": `"io.debezium.transforms.outbox.EventRouter`",
    `"transforms.outbox.route.topic.replacement`": `"payment.`${routedByValue}`",
    `"transforms.outbox.table.fields.additional.placement`": `"event_type:header`"
  }
}
"@

$response = Invoke-RestMethod 'http://localhost:8083/connectors' -Method 'POST' -Headers $headers -Body $body
$response | ConvertTo-Json

$headers = New-Object "System.Collections.Generic.Dictionary[[String],[String]]"
$headers.Add("Content-Type", "application/json")

$body = @"
{
  `"name`": `"debezium-postgres-inventory-events`",
  `"config`": {
    `"connector.class`": `"io.debezium.connector.postgresql.PostgresConnector`",
    `"tasks.max`": `"1`",
    `"database.hostname`": `"inventory-service-db`",
    `"database.port`": `"5432`",
    `"database.user`": `"user`",
    `"database.password`": `"password`",
    `"database.dbname`" : `"inventory-service`",
    `"database.server.name`": `"PostgreSQL-14`",
    `"topic.prefix`": `"debezium`",
    `"table.include.list`": `"public.inventory_outbox_events`",
    `"tombstones.on.delete`" : `"false`",
    `"slot.name`" : `"inventory_outbox_events_slot`",
    `"plugin.name`": `"pgoutput`",
    `"transforms`": `"outbox`",
    `"transforms.outbox.type`": `"io.debezium.transforms.outbox.EventRouter`",
    `"transforms.outbox.route.topic.replacement`": `"inventory.`${routedByValue}`",
    `"transforms.outbox.table.fields.additional.placement`": `"event_type:header`"
  }
}
"@

$response = Invoke-RestMethod 'http://localhost:8083/connectors' -Method 'POST' -Headers $headers -Body $body
$response | ConvertTo-Json
