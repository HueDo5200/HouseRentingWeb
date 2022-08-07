package com.self.house.renting.config;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.filter.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {
    private Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator mappingRoute(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(Constants.RENTING_SERVICE_ID, r -> r.path(Constants.RENTING_SERVICE_PATH)
                        .and().method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f.filter(filter))
                        .uri(Constants.RENTING_SERVICE_URI))
                .route(Constants.MESSAGE_SERVICE_ID, p -> p.path(Constants.MESSAGE_SERVICE_PATH)
                        .and().method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f.filter(filter))
                        .uri(Constants.MESSAGE_SERVICE_URI))
                .route(Constants.AUTHENTICATION_SERVICE_ID,  p -> p.path(Constants.AUTHENTICATION_SERVICE_PATH)
                        .and().method(HttpMethod.POST, HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(filter))
                        .uri(Constants.AUTHENTICATION_SERVICE_URI)
                )
                .build();
    }
}
