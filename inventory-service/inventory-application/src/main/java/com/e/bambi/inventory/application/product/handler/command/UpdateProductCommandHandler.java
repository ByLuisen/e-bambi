package com.e.bambi.inventory.application.product.handler.command;

import com.e.bambi.inventory.application.InventoryApplicationServiceImpl;
import com.e.bambi.inventory.application.product.dto.command.UpdateProductCommand;
import com.e.bambi.inventory.application.product.dto.response.ProductResponse;
import com.e.bambi.inventory.application.product.mapper.ProductApplicationMapper;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductRepository;
import com.e.bambi.inventory.domain.product.entity.Product;
import com.e.bambi.inventory.domain.exception.ProductNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateProductCommandHandler implements CommandHandler<Mono<ProductResponse>, UpdateProductCommand> {

    private final ProductRepository productRepository;
    private final ProductApplicationMapper productApplicationMapper;
    private final InventoryApplicationServiceImpl inventoryApplicationService;

    @Override
    public Mono<ProductResponse> handle(UpdateProductCommand command) {
        Product product = productApplicationMapper.updateProductCommandToProduct(command);

        return inventoryApplicationService.ensureProductIsValid(product)
                .then(productRepository.findById(command.getProductId()))
                .switchIfEmpty(
                        Mono.error(new ProductNotFoundException("Product with id: " + product.getId().getValue() +
                                " could not be found"))
                )
                .flatMap(obtainedProduct -> {
                    obtainedProduct.updateProduct(product);
                    return productRepository.updated(obtainedProduct);
                })
                .onErrorMap(DuplicateKeyException.class, e ->
                        new DuplicateKeyException("Please, ensure the product has unique properties")
                )
                .map(productApplicationMapper::toProductResponse);
    }
}
