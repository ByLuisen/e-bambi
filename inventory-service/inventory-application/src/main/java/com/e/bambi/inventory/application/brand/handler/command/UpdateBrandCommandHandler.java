package com.e.bambi.inventory.application.brand.handler.command;

import com.e.bambi.inventory.application.brand.dto.command.UpdateBrandCommand;
import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.application.brand.mapper.BrandApplicationMapper;
import com.e.bambi.inventory.application.brand.port.outbound.repository.BrandRepository;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateBrandCommandHandler implements CommandHandler<Mono<BrandResponse>, UpdateBrandCommand> {

    private final BrandRepository brandRepository;
    private final BrandApplicationMapper brandApplicationMapper;

    @Override
    public Mono<BrandResponse> handle(UpdateBrandCommand command) {
        return brandRepository
                .update(brandApplicationMapper.updateBrandCommandToBrand(command))
                .onErrorMap(DuplicateKeyException.class, e -> {
                    log.error("Brand with name: {} already exists", command.getName());
                    return new DuplicateKeyException("Brand with name " + command.getName() + "already exists");
                })
                .map(brandApplicationMapper::toBrandResponse);
    }
}
