package com.self.house.renting.filter;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.util.JwtTokenUtil;
import com.self.house.renting.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthenticationFilter implements GatewayFilter {

    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private JwtTokenUtil tokenUtil;

    private RequestUtil requestUtil;

    @Autowired
    public AuthenticationFilter(JwtTokenUtil tokenUtil, RequestUtil requestUtil) {
        this.tokenUtil = tokenUtil;
        this.requestUtil = requestUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request =  exchange.getRequest();
       if(requestUtil.secured.test(request)) {
            if(!isValidHeader(request)) {
                  return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            final String token = getTokenHeader(request);
            if(!tokenUtil.validateToken(token)) {
                return this.onError(exchange,  HttpStatus.UNAUTHORIZED);
            }
       }
       return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange,  HttpStatus status) {
        ServerHttpResponse response =  exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    private boolean isValidHeader(ServerHttpRequest request) {
        return request.getHeaders().containsKey(Constants.AUTHORIZATION_HEADER);
    }

    private String getTokenHeader(ServerHttpRequest request) {
        String tokenBearer = request.getHeaders().getOrEmpty(Constants.AUTHORIZATION_HEADER).get(0);
        return tokenBearer != null && !tokenBearer.isEmpty() ? tokenBearer.substring(7) : null;
    }


}
