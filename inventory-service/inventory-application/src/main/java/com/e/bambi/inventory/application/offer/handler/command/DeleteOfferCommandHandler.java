package com.e.bambi.inventory.application.offer.handler.command;

import com.e.bambi.inventory.application.offer.dto.command.DeleteOfferCommand;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferRepository;
import com.e.bambi.inventory.domain.exception.OfferNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteOfferCommandHandler implements CommandHandler<Mono<Void>, DeleteOfferCommand> {

    private final OfferRepository offerRepository;

    @Override
    public Mono<Void> handle(DeleteOfferCommand command) {
        return offerRepository.deleteByIdAndSupplierId(
                command.getOfferId(),
                command.getSupplierId()
        ).handle((updatedRows, sink) -> {
            if (updatedRows < 1) {
                sink.error(new OfferNotFoundException("Offer with id: " + command.getOfferId() + " could not be found"));
            } else {
                log.info("Offer with id: {}, has successfully deleted", command.getOfferId());
                sink.complete();
            }
        });
    }
}
