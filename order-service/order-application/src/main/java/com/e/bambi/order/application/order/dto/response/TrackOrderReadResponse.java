package com.e.bambi.order.application.order.dto.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.e.bambi.order.domain.order.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Getter
public class TrackOrderReadResponse {

    private final String orderStatus;
    private final List<String> failureMessages;

    public TrackOrderReadResponse(String orderStatus, String failureMessages) {
        this.orderStatus = orderStatus;
        this.failureMessages = new ArrayList<>(Arrays.asList(failureMessages.split(FAILURE_MESSAGE_DELIMITER)));
    }
}
