package com.e.bambi.inventory.application.product.handler.command;

import com.e.bambi.inventory.application.InventoryApplicationServiceImpl;
import com.e.bambi.inventory.application.product.dto.command.CreateProductCommand;
import com.e.bambi.inventory.application.product.dto.response.ProductResponse;
import com.e.bambi.inventory.application.product.mapper.ProductApplicationMapper;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductRepository;
import com.e.bambi.inventory.domain.product.entity.Product;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateProductCommandHandler implements CommandHandler<Mono<ProductResponse>, CreateProductCommand> {

    private final ProductRepository productRepository;
    private final ProductApplicationMapper productApplicationMapper;
    private final InventoryApplicationServiceImpl inventoryApplicationService;

    @Override
    public Mono<ProductResponse> handle(CreateProductCommand command) {
        Product product = productApplicationMapper.createProductCommandToProduct(command);

        return inventoryApplicationService.ensureProductIsValid(product)
                .then(Mono.defer(() -> {
                    product.initializeProduct();
                    return productRepository.insert(product);
                }))
                .onErrorMap(DuplicateKeyException.class, e ->
                        new DuplicateKeyException("Please, ensure the product has unique properties")
                )
                .map(productApplicationMapper::toProductResponse);
    }

}
