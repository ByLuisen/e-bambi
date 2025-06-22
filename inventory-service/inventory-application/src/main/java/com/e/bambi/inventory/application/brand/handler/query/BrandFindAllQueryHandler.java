package com.e.bambi.inventory.application.brand.handler.query;

import com.e.bambi.inventory.application.brand.dto.query.BrandFindAllQuery;
import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.application.brand.port.outbound.repository.BrandQueryRepository;
import com.e.bambi.inventory.domain.exception.BrandNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrandFindAllQueryHandler implements QueryHandler<Flux<BrandResponse>, BrandFindAllQuery> {

    private final BrandQueryRepository brandQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<BrandResponse> handle(BrandFindAllQuery query) {
        return brandQueryRepository.findAll()
                .switchIfEmpty(Mono.error(new BrandNotFoundException("No brand could be found")));
    }
}
