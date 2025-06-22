package com.e.bambi.inventory.application.supplier.handler.command;

import com.e.bambi.inventory.application.supplier.dto.command.DeleteSupplierCommand;
import com.e.bambi.inventory.application.supplier.port.outbound.repository.SupplierRepository;
import com.e.bambi.inventory.domain.exception.SupplierNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteSupplierCommandHandler implements CommandHandler<Mono<Void>, DeleteSupplierCommand> {

    private final SupplierRepository supplierRepository;

    @Override
    public Mono<Void> handle(DeleteSupplierCommand command) {
        UUID supplierId = command.getSupplierId().getValue();

        return supplierRepository.deleteById(command.getSupplierId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        log.error("Supplier with id: {} could not be found", supplierId);
                        sink.error(new SupplierNotFoundException("Supplier with id: " + supplierId
                                + "could not be found"));
                    } else {
                        log.info("Supplier with id: {} successfully deleted", supplierId);
                        sink.complete();
                    }
                });
    }
}
