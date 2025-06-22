package com.e.bambi.inventory.bootstrap.config;

import com.e.bambi.inventory.domain.InventoryDomainService;
import com.e.bambi.inventory.domain.InventoryDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public InventoryDomainService inventoryDomainService() {
        return new InventoryDomainServiceImpl();
    }
}
