package com.e.bambi.payment.bootstrap.config;

import com.e.bambi.payment.domain.PaymentDomainService;
import com.e.bambi.payment.domain.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }
}
