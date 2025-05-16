package com.e.bambi.order.application.dto.response;

import java.util.List;
import java.util.UUID;

public record TrackOrderResponse(UUID orderTrackingId, String orderStatus, List<String> failureMessages) {
}
