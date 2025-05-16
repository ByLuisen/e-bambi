package com.e.bambi.shared.infrastructure.messaging.kafka.consumer.exception;


import lombok.Getter;
import reactor.kafka.receiver.ReceiverRecord;

@Getter
@SuppressWarnings("serial")
public class ReceiverRecordException extends RuntimeException {
    private final ReceiverRecord record;

    ReceiverRecordException(ReceiverRecord record, Throwable t) {
        super(t);
        this.record = record;
    }
}
