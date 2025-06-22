package com.e.bambi.inventory.application.supplier.handler.command;

import com.e.bambi.inventory.application.supplier.dto.command.UpdateSupplierCommand;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.inventory.application.supplier.mapper.SupplierApplicationMapper;
import com.e.bambi.inventory.application.supplier.port.outbound.repository.SupplierRepository;
import com.e.bambi.inventory.domain.exception.SupplierNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateSupplierCommandHandler implements CommandHandler<Mono<SupplierResponse>, UpdateSupplierCommand> {

    private final SupplierRepository supplierRepository;
    private final SupplierApplicationMapper supplierApplicationMapper;

    @Override
    public Mono<SupplierResponse> handle(UpdateSupplierCommand command) {
        return supplierRepository.update(
                        supplierApplicationMapper.toSupplier(command)
                ).switchIfEmpty(
                        Mono.error(new SupplierNotFoundException("Supplier with id: " +
                                command.getSupplierId().getValue() + " could not be found"))
                ).onErrorMap(DuplicateKeyException.class, e -> {
                    log.error("Supplier with name: {} already exists", command.getName());
                    return new DuplicateKeyException("Supplier with name: " + command.getName() + " already exists");
                })
                .map(supplierApplicationMapper::toSupplierResponse);
    }
}
