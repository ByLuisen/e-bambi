package com.e.bambi.inventory.application.product.handler.command;

import com.e.bambi.inventory.application.product.dto.command.DeleteProductCommand;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductRepository;
import com.e.bambi.inventory.domain.exception.ProductNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeleteProductCommandHandler implements CommandHandler<Mono<Void>, DeleteProductCommand> {

    private final ProductRepository productRepository;

    @Override
    public Mono<Void> handle(DeleteProductCommand command) {
        return productRepository.deleteById(command.getProductId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        sink.error(new ProductNotFoundException("Product with id: " + command.getProductId().getValue() +
                                " could not be found"));
                    } else {
                        sink.complete();
                    }
                });
    }
}
