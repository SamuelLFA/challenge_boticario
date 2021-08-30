package br.com.samuellfa.cashback.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.client.RestTemplate


@Configuration
class BeanConfiguration {

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun methodValidationPostProcessor() = MethodValidationPostProcessor()
}