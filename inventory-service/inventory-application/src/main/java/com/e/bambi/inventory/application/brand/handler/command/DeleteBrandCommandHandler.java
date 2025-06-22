package com.e.bambi.inventory.application.brand.handler.command;

import com.e.bambi.inventory.application.brand.dto.command.DeleteBrandCommand;
import com.e.bambi.inventory.application.brand.port.outbound.repository.BrandRepository;
import com.e.bambi.inventory.domain.exception.BrandNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteBrandCommandHandler implements CommandHandler<Mono<Void>, DeleteBrandCommand> {

    private final BrandRepository brandRepository;

    @Override
    public Mono<Void> handle(DeleteBrandCommand command) {
        return brandRepository.deleteById(command.getBrandId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        log.error("Brand with id: {} could not be found", command.getBrandId().getValue());
                        sink.error(new BrandNotFoundException("Brand with id: " + command.getBrandId().getValue() +
                                " could not be found"));
                    } else {
                        log.info("Brand with id: {} has successfully deleted", command.getBrandId().getValue());
                        sink.complete();
                    }
                });
    }
}
