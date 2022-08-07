package com.self.house.renting.util;

import com.self.house.renting.constants.Constants;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RequestUtil {

    public static final List<String> openEndpoints = List.of(Constants.LOGIN_API, Constants.REGISTER_API, Constants.PUBLIC_AMENITY_API,
            Constants.PUBLIC_PROPERTY_TYPE_API, Constants.PUBLIC_PROPERTY_API
    );

    public Predicate<ServerHttpRequest> secured = httpServerRequest -> openEndpoints.stream().noneMatch(api -> httpServerRequest.getURI().getPath().contains(api));

}
