package com.self.house.renting.util;

import com.self.house.renting.model.dto.response.ImageResponse;
import com.self.house.renting.model.dto.response.PropertyResponse;
import com.self.house.renting.model.dto.response.ReservationResponse;
import com.self.house.renting.model.entity.Property;
import com.self.house.renting.model.entity.PropertyImage;
import com.self.house.renting.model.entity.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class DtoUtil {
    public static List<PropertyResponse> toPropertyResponses(List<Property> properties) {
        return properties != null && !properties.isEmpty() ? properties.stream().map(Property::toPropertyResponse).collect(Collectors.toList()) : null;
    }

    public static List<ImageResponse> toImageResponseList(List<PropertyImage> images) {
        return images != null && !images.isEmpty() ? images.stream().map(PropertyImage::toImageResponse).collect(Collectors.toList()) : null;
    }

    /**
     *
     * @param reservations not null or empty
     * @return list of reservation responses
     */
    public static List<ReservationResponse> toReservationResponse(List<Reservation> reservations) {
        return reservations.stream().map(Reservation::getReservationResponseFromDto).collect(Collectors.toList());
    }

}
