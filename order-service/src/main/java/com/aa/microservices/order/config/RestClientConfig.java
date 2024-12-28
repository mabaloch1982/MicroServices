package com.aa.microservices.order.config;

import com.aa.microservices.order.client.InventoryClientHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${inventory.uri}")
    private String inventoryUrl;

    @Bean
    public InventoryClientHttp inventoryClient() {
        RestClient restClient = RestClient.builder()
            .baseUrl(inventoryUrl)
            .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClientHttp.class);
    }
}
