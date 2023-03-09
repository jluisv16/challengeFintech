package com.ontop.challenge.transaction.infrastructure.conf;


import com.ontop.challenge.transaction.application.service.DomainTransactionService;
import com.ontop.challenge.transaction.application.service.TransactionService;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanTransactionConfiguration {

    @Bean
    TransactionService transactionBeanService(final TransactionRepository transactionRepository){
        return new DomainTransactionService(transactionRepository);
    }

    @Bean("restTemplate")
    public RestTemplate restTemplate(){

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);

        return new RestTemplate(factory);
    }


}
