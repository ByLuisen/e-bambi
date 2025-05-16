package com.e.bambi.order.application.message.listener.inventory;

import com.e.bambi.order.application.dto.message.InventoryResponse;
import com.e.bambi.order.application.port.inbound.message.listener.inventory.InventoryApprovedMessageListener;

public class InventoryApprovedMessageListenerImpl implements InventoryApprovedMessageListener {
    @Override
    public void orderApproved(InventoryResponse inventoryResponse) {

    }

    @Override
    public void orderRejected(InventoryResponse inventoryResponse) {

    }
}
