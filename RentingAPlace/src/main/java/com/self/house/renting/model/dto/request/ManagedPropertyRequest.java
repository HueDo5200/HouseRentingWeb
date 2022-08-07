package com.self.house.renting.model.dto.request;

import com.self.house.renting.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagedPropertyRequest {

    private int id;
    @NotBlank(message = "Property name should not be blank")
    private String name;
    @NotBlank(message = "Description name should not be blank")
    private String description;
    @Min(0)
    private BigDecimal avgRating;
    @Min(0)
    private int ratingNum;
    @Min(1)
    private BigDecimal pricePerNight;
    @Min(1)
    private int bedroom;
    @Min(1)
    private int bathroom;
    @Min(1)
    private int kitchen;

    @NotNull
    private Location location;
    @NotNull
    private PropertyType propertyType;
    @NotNull
    private List<PropertyImage> propertyImages;
    @NotNull
    private Users user;

    private List<Amenity> amenities;


    /**
     *
     * @return Property converted from ManagedPropertyRequest
     */
    public Property fromDtoToProperty() {
        return Property.builder().id(getId())
                .name(getName())
                .description(getDescription())
                .avgRating(getAvgRating())
                .ratingNum(getRatingNum())
                .bathroom(getBathroom())
                .kitchen(getKitchen())
                .bedroom(getBedroom())
                .propertyImages(getPropertyImages())
                .pricePerNight(getPricePerNight())
                .propertyType(getPropertyType())
                .location(getLocation())
                .owner(getUser())
                .amenities(getAmenities())
                .build();
    }



}
