package com.self.house.renting.constants;

public class Constants {
    public static final String LOGIN_API = "/api/v1/auth/signin";
    public static final String REGISTER_API = "/api/v1/auth/register";
    public static final String PUBLIC_AMENITY_API = "/api/v1/renting/amenities";
    public static final String PUBLIC_PROPERTY_API = "/api/v1/renting/public";
    public static final String PUBLIC_PROPERTY_TYPE_API = "/api/v1/renting/property-types";
    public static final String INVALID_HEADER = "Invalid header";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT Token";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN";
    public static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH";
    public static final String ALLOWED_ORIGIN = "*";
    public static final String MAX_AGE = "7200";
    public static final String ALLOW_CROSS_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    public static final String ALLOW_METHOD_HEADER = "Access-Control-Allow-Methods";
    public static final String MAX_AGE_HEADER = "Access-Control-Max-Age";
    public static final String ALLOW_HEADER = "Access-Control-Allow-Headers";

    public static final String RENTING_SERVICE_ID = "renting-service";
    public static final String MESSAGE_SERVICE_ID = "message-service";
    public static final String AUTHENTICATION_SERVICE_ID = "auth-service";
    public static final String RENTING_SERVICE_PATH = "/api/v1/renting/**";
    public static final String MESSAGE_SERVICE_PATH = "/api/v1/chat/**";
    public static final String AUTHENTICATION_SERVICE_PATH = "/api/v1/auth/**";
    public static final String RENTING_SERVICE_URI = "lb://RENT-A-PLACE-SERVICE";
    public static final String MESSAGE_SERVICE_URI = "lb://MESSAGE-SERVICE";
    public static final String AUTHENTICATION_SERVICE_URI = "lb://AUTHENTICATION-SERVICE";



}
