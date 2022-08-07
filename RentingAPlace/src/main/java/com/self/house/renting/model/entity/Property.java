package com.self.house.renting.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.self.house.renting.constants.Constants;
import com.self.house.renting.util.DtoUtil;
import com.self.house.renting.model.dto.response.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "id")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @Column(name="avg_rating")
    private BigDecimal avgRating;

    @Column(name="rating_num")
    private int ratingNum;

    @Column(name="price_by_night", columnDefinition = "integer default 100")
    private BigDecimal pricePerNight;
    private int bedroom;
    private int bathroom;
    private int kitchen;

    @ManyToOne@JoinColumn(name="user_id")
    private Users owner;

    @OneToOne(cascade = CascadeType.ALL)@JoinColumn(name="location_id")
    private Location location;

    @ManyToOne(cascade = CascadeType.MERGE)@JoinColumn(name="property_type_id")
    private PropertyType propertyType;

    @ManyToMany
    @JoinTable(name="property_amenity", joinColumns = @JoinColumn(name="property_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="amenity_id", referencedColumnName = "id"))
    private List<Amenity> amenities;

	@JsonManagedReference(value = "images")
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyImage> propertyImages;

    @JsonManagedReference(value = "property")
    @OneToMany(mappedBy = "property")
    private List<Reservation> reservations;


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    public ManagedPropertyResponse toManagedProperty() {
        return ManagedPropertyResponse
                .builder()
                .id(getId())
                .bathroom(getBathroom())
                .bedroom(getBedroom())
                .kitchen(getKitchen())
                .name(getName())
                .description(getDescription())
                .pricePerNight(getPricePerNight())
                .propertyImages(toImageResponseList())
                .propertyType(
                        TypeResponse.builder().id(getPropertyType().getId()).name(getPropertyType().getName()).build())
                .avgRating(getAvgRating())
                .ratingNum(getRatingNum())
                .location(getLocation().toLocationResponse())
                .build();

    }

    /**
     *
     * @param customerId > 0
     * @return
     *      if property currently reserved by the customer
     *              return the property detailed information of the selected property, including
     *              its information as well as reversed information of that property
     *      else if the reservation already ended or property has not reserved by that customer
     *              return the property detailed information of the selected property,
     *              as well as reservation information of other customers - only start date, end date
     *
     */
    public PropertyDetailedResponse toPropertyDetailedResponse(int customerId) {
        Reservation currentReservation = getCurrentReservationOfCustomer(getReservations(), customerId);
        List<CurrentReservationResponse> currentReservationListOfProperty = getCurrentReservationListOfProperty(getReservations());
        if(currentReservation != null) {
            return convertPropertyToPropertyDetailedWithCustomerReservationInfo(currentReservation);
        } else {
            return convertPropertyToPropertyDetailedWithPropertyReservationInfo(currentReservationListOfProperty);
        }
    }


    /**
     *
     * @param list must not be null or empty
     * @param customerId > 0
     * @return
     *          current reservation information of customer
     */
    public Reservation getCurrentReservationOfCustomer(List<Reservation> list, int customerId) {
        Optional<Reservation> data = list.stream().filter(reservation -> reservation.getUsers().getId() == customerId && reservation.getEndDate().toLocalDate().compareTo(LocalDate.now()) >= 0).map(reservation -> Reservation.builder().startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .total(reservation.getTotal())
                .build()).findFirst();
        return data.orElse(null);
    }

    /**
     *
     * @param list must not be null or empty
     * @return
     *          all reservation information: start date, end date of all customer of a property
     */
    public List<CurrentReservationResponse> getCurrentReservationListOfProperty(List<Reservation> list) {
        return  list.stream().filter(r -> r.getEndDate().toLocalDate().compareTo(LocalDate.now()) >= 0)
                .map(reservation -> CurrentReservationResponse.builder()
                        .startDate(reservation.getStartDate())
                        .endDate(reservation.getEndDate()).build())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param currentReservation - must not be null
     * @return
     *        return detailed information of the property with reservation information:
     *        start date, end date, total, status
     */
    public PropertyDetailedResponse convertPropertyToPropertyDetailedWithCustomerReservationInfo(Reservation currentReservation) {
        Location place = getLocation();
        return PropertyDetailedResponse.builder().id(getId()).name(getName()).description(getDescription())
                .avgRating(getAvgRating()).ratingNum(getRatingNum())
                .bedroom(getBedroom())
                .bathroom(getBathroom())
                .kitchen(getKitchen())
                .pricePerNight(getPricePerNight())
                .ownerId(getOwner().getId())
                .ownerName(getOwner().getUsername())
                .ownerEmail(getOwner().getEmail())
                .location(LocationResponse.builder().id(place.getId()).state(place.getState()).city(place.getCity()).details(place.getDetails()).description(place.getDescription()).build())
                .typeName(getPropertyType().getName())
                .amenities(toAmenityResponseList())
                .propertyImages(toImageResponseList())
                .startDate(currentReservation.getStartDate())
                .endDate(currentReservation.getEndDate())
                .total(currentReservation.getTotal())
                .reservationStatus(currentReservation.getStatus()).build();
    }

    /**
     *
     *
     * @param currentReservationList - must not be null or empty
     * @return
     *         return the property detailed information with all of its future reservation information:
     *         start date, end date
     */
    public PropertyDetailedResponse convertPropertyToPropertyDetailedWithPropertyReservationInfo(List<CurrentReservationResponse> currentReservationList) {
        Location place = getLocation();
        return PropertyDetailedResponse.builder().id(getId()).name(getName()).description(getDescription())
                .avgRating(getAvgRating()).ratingNum(getRatingNum())
                .bedroom(getBedroom())
                .bathroom(getBathroom())
                .kitchen(getKitchen())
                .pricePerNight(getPricePerNight())
                .ownerId(getOwner().getId())
                .ownerName(getOwner().getUsername())
                .ownerEmail(getOwner().getEmail())
                .location(LocationResponse.builder().id(place.getId()).state(place.getState()).city(place.getCity()).details(place.getDetails()).description(place.getDescription()).build())
                .typeName(getPropertyType().getName())
                .amenities(toAmenityResponseList())
                .propertyImages(toImageResponseList())
                .reservationStatus(Constants.NOT_RESERVED)
                .currentReservations(currentReservationList)
                .build();
    }

    /**
     *
     * @return an object with property id, name and list of its images
     */
    public PropertyImagesResponse getImagesListFromProperty() {
        return PropertyImagesResponse.builder().propertyId(getId())
                .propertyName(getName())
                .propertyImages(toImageResponseList())
                .build();
    }

    public List<ImageResponse> toImageResponseList() {
        return getPropertyImages().stream().map(PropertyImage::toImageResponse).collect(Collectors.toList());
    }

    public List<AmenityResponse> toAmenityResponseList() {
        return getAmenities().stream().map(Amenity::convertAmenityToDto).collect(Collectors.toList());
    }

    public PropertyResponse toPropertyResponse() {
        return PropertyResponse
                .builder()
                .id(getId())
                .name(getName())
                .propertyImages(DtoUtil.toImageResponseList(getPropertyImages()))
                .bedroom(getBedroom())
                .bathroom(getBathroom())
                .kitchen(getKitchen())
                .pricePerNight(getPricePerNight())
                .build();
    }



}
