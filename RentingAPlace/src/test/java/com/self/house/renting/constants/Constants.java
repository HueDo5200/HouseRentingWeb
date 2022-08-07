package com.self.house.renting.constants;

public class Constants {
    public static final String AMENITY_API = "/api/v1/amenities";
    public static final String PROPERTY_API = "/api/v1/properties";
    public static final String GET_LIST_SUCCESS = "Get list of data successfully!";
    public static final String GET_LIST_FAILED = "Get list of data failed!";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String RESOURCE_ALREADY_EXISTED = "Resource already existed";
    public static final String ADD_RESOURCE_SUCCESS = "Adding new resource successfully!";
    public static final String UPDATE_RESOURCE_SUCCESS = "Updating resource successfully!";
    public static final String UPDATE_RESOURCE_FAILED = "Updating resource failed!";
    public static final String DELETE_RESOURCE_SUCCESS = "Deleting resource successfully!";
    public static final String GET_RESOURCE_SUCCESS = "Getting resource successfully!";
    public static final String EMPTY_LIST = "The list is empty!";
    public static final String SEARCH_LIST_RESULT = "Searching resource successfully!";
    public static final String RESERVE_SUCCESS = "Your property has been reserved!";
    public static final String RESERVATION_SUBJECT_TITLE = "Success Reservation Notification";
    public static final String UPLOAD_IMAGE_SUCCESS = "Upload image successfully!";
    public static final String UPLOAD_IMAGE_FAILED = "Upload image successfully!";
    public static final String NO_RESERVATION = "No reservation";
    public static final String USER_DISABLED = "User disabled";
    public static final String WRONG_AUTHENTICATION = "Wrong username and password";
    public static final String REGISTER_SUCCESS = "Register successfully";
    public static final String REGISTER_FAILED = "Register failed! Username already existed.";
    public static final String LOGIN_SUCCESS = "Login successfully!";
    public static final String VALIDATION_ERROR = "Data sent is not valid";
    public static final String PAGE_OUT_OF_BOUND_EXCEPTION = "Page size is out of bounds";
    public static final String PROPERTY_RESERVED = "Property is reserved for the requested time";
    public static final String INVALID_DATE_RANGE = "Check in, check out date should be after current date. Check in Date need to be before check out date";
    public static final int UPDATE_RESOURCE_STATUS_FAILED = -1;
    public static final int WAITING_RESERVATION_STATUS = 0;
    public static final int RESERVED_STATUS = 1;
    public static final int NOT_RESERVED = -1;
    public static final int COMPLETED_RESERVATION_STATUS = 2;
    public static final String DEFAULT_LOCATION_VALUE = "Location";
    public static final String DEFAULT_TYPE_VALUE = "Type";
    public static final String DEFAULT_AMENITY_VALUE = "Amenity";
    public static final String OWNER_ROLE = "owner";
    public static final String CUSTOMER_ROLE = "customer";
    public static final String NOT_OWNER = "Only owner can has the permission to access";
    public static final String NOT_CUSTOMER = "Only customer can has the permission to access";

    public static final String VST = "Asia/Bangkok";
    public static final String IST = "India Standard Time";
    public static final String JST = "Japan Standard Time";
    public static final String SENDING_EMAIL_FAILED = "Sending email failed";

}
