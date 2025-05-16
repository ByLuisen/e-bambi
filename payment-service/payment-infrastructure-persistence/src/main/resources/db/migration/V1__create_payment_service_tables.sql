CREATE TABLE IF NOT EXISTS payment_methods (
    id UUID NOT NULL,
    name VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT pk_payment_methods PRIMARY KEY (id),
    CONSTRAINT uniq_payment_methods_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS payment_outbox_events (
  id UUID NOT NULL,
  aggregatetype VARCHAR(255) NOT NULL,
  aggregateid VARCHAR(255) NOT NULL,
  event_type VARCHAR(255),
  payload JSONB,
  CONSTRAINT pk_payment_outbox_events PRIMARY KEY (id)
);