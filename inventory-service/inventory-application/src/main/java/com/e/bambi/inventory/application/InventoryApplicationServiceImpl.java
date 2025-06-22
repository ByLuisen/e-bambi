package com.e.bambi.inventory.application;


import com.e.bambi.inventory.application.brand.port.outbound.repository.BrandQueryRepository;
import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentQueryRepository;
import com.e.bambi.inventory.application.productstatus.port.outbound.repository.ProductStatusQueryRepository;
import com.e.bambi.inventory.domain.product.entity.Product;
import com.e.bambi.inventory.domain.exception.ProductBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryApplicationServiceImpl implements InventoryApplicationService {

    private final BrandQueryRepository brandQueryRepository;
    private final DepartmentQueryRepository departmentQueryRepository;
    private final ProductStatusQueryRepository productStatusQueryRepository;

    @Override
    public Mono<Void> ensureProductIsValid(Product product) {
        List<String> errors = new ArrayList<>(3);

        Mono<Boolean> brandExists = brandQueryRepository.existsById(product.getBrandId());
        Mono<Boolean> departmentExists = departmentQueryRepository.existsById(product.getDepartmentId());
        Mono<Boolean> productStatusExists = productStatusQueryRepository.existsById(product.getProductStatusId());

        return Mono.zip(brandExists, departmentExists, productStatusExists)
                .flatMap(t -> {
                    if (!t.getT1()) {
                        errors.add("Brand with id: " + product.getBrandId().getValue() + " does not exists");
                    }
                    if (!t.getT2()) {
                        errors.add("Department with id: " + product.getDepartmentId().getValue() + " does not exists");
                    }
                    if (!t.getT3()) {
                        errors.add("Product status with id: " + product.getProductStatusId().getValue() + " does not exists");
                    }

                    if (!errors.isEmpty()) {
                        log.error("Product could not be created");
                        return Mono.error(new ProductBadRequestException("Product could not be created", errors));
                    }

                    return Mono.empty();
                });
    }
}
