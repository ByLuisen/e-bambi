package com.e.bambi.order.application.port.inbound.message.listener.inventory;

import com.e.bambi.order.application.dto.message.InventoryResponse;

public interface InventoryApprovedMessageListener {
    void orderApproved(InventoryResponse inventoryResponse);
    void orderRejected(InventoryResponse inventoryResponse);
}
