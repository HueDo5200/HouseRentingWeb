package com.self.house.renting.service;

import com.self.house.renting.constants.Constants;
import com.self.house.renting.exception.ResourceExistedException;
import com.self.house.renting.exception.ResourceNotFoundException;
import com.self.house.renting.model.dto.request.ManagedPropertyRequest;
import com.self.house.renting.repository.PropertyRepository;
import com.self.house.renting.service.serviceimpl.PropertyServiceImpl;
import com.self.house.renting.model.dto.response.*;
import com.self.house.renting.model.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PropertyServiceUnitTest {

    private Logger logger = LoggerFactory.getLogger(PropertyServiceUnitTest.class);
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyServiceImpl propertyServiceImpl;

    @Autowired
    private PropertyRepository propertyRepository;

    private PropertyResponse response1;
    private PropertyResponse response2;
    private PropertyResponse response3;

    private ImageResponse image1;
    private ImageResponse image2;
    private ImageResponse image3;
    private ImageResponse image4;
    private ImageResponse image5;
    private ImageResponse image6;
    private ImageResponse image7;
    private ImageResponse image8;
    private ImageResponse image9;
    private ImageResponse image10;
    private ImageResponse image11;

    private List<ImageResponse> images1;
    private List<ImageResponse> images2;
    private List<ImageResponse> images3;

    private ManagedPropertyResponse managedPropertyResponse1;
    private ManagedPropertyResponse managedPropertyResponse2;
    private ManagedPropertyResponse managedPropertyResponse3;

    @BeforeAll
    public void setup() {
         image9 = ImageResponse.builder().id(9).name("property5-1.jpg").build();
         image10 = ImageResponse.builder().id(10).name("property5-2.jpg").build();
        image11 = ImageResponse.builder().id(11).name("property5-3.jpg").build();
        images3 = new ArrayList<>();
        images3.add(image9);
        images3.add(image10);
        images3.add(image11);

        image6 = ImageResponse.builder().id(6).name("property2-1.jpg").build();
        image7 = ImageResponse.builder().id(7).name("property2-2.jpg").build();
        image8 = ImageResponse.builder().id(8).name("property2-3.jpg").build();
        images2 = new ArrayList<>();
        images2.add(image6);
        images2.add(image7);
        images2.add(image8);

         image1 = ImageResponse.builder().id(1).name("property1-1.jpg").build();
        image2 = ImageResponse.builder().id(2).name("property1-2.jpg").build();
        image3 = ImageResponse.builder().id(3).name("property1-3.jpg").build();
        image4 = ImageResponse.builder().id(4).name("property1-4.jpg").build();
        image5 = ImageResponse.builder().id(5).name("property1-5.jpg").build();
        images1 = new ArrayList<>();
        images1.add(image1);
        images1.add(image2);
        images1.add(image3);
        images1.add(image4);
        images1.add(image5);


       response3 = PropertyResponse.builder().id(3).name("Nha ben bien")
                .propertyImages(images3)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .pricePerNight(new BigDecimal("101"))
                .build();

        response2 = PropertyResponse.builder().id(2).name("New Studio, lift, Hoan Kiem, near old quarter #B01")
                .propertyImages(images2)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .pricePerNight(new BigDecimal("938"))
                .build();

       response1 = PropertyResponse.builder().id(1).name("Nha Ben Run(U Lesa) - Big Pine House")
                .propertyImages(images1)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .pricePerNight(new BigDecimal("101"))
                .build();

       managedPropertyResponse3 = ManagedPropertyResponse.builder()
               .id(3)
               .name("Nha ben bien")
               .description("The Big Pine House is located right on the hillside, the surrounding old pine forest brings a cool vibe all year round for Homestay. The house also owns an extremely spacious front view when the brick wall is replaced by transparent toughened glass wall, without visibility restrictions.")
               .avgRating(new BigDecimal("4.4"))
               .ratingNum(11)
               .bathroom(1)
               .bedroom(1)
               .kitchen(1)
               .pricePerNight(new BigDecimal("101"))
               .location(LocationResponse.builder().id(5).state("Vietnam").city("Hanoi").details("Minh Phu").description(null).build())
               .propertyImages(images3)
               .propertyType(TypeResponse.builder().id(3).name("villa").build())
               .build();

    }


    @Test
    public void shouldReturnManagedPropertyResponseWhenSaveNewProperty() {

        ManagedPropertyResponse expected = ManagedPropertyResponse.builder()
                .id(4)
                .name("Nha ben ho")
                .avgRating(new BigDecimal("0"))
                .ratingNum(0)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .description("A beautiful house")
                .pricePerNight(new BigDecimal("50"))
                .propertyImages(new ArrayList<>())
                .propertyType(TypeResponse.builder().id(1).name("flat").build())
                .location(LocationResponse.builder().id(6).state("Vietnam").city("Hanoi").details("100 Nguyen Trai").description(null).build())
                .build();

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(new Amenity(1, "pool facing", null));
        amenities.add(new Amenity(2, "beach facing", null));
        ManagedPropertyRequest request = ManagedPropertyRequest.builder()
                .name("Nha ben ho")
                .avgRating(new BigDecimal("0"))
                .ratingNum(0)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .description("A beautiful house")
                .pricePerNight(new BigDecimal("50"))
                .propertyImages(new ArrayList<>())
                .amenities(amenities)
                .propertyType(PropertyType.builder().id(1).name("flat").build())
                .location(Location.builder().state("Vietnam").city("Hanoi").details("100 Nguyen Trai").build())
                .user(Users.builder().id(5).email("vietanh@gmail.com").firstName("Anh").lastName("Viet").username("Viet Anh").phone("0333033094").role(Role.builder().id(1).name("owner").build()).build())
                .build();

       ManagedPropertyResponse actual = propertyService.save(request);
       assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
       assertEquals(expected.getAvgRating(), actual.getAvgRating());
       assertEquals(expected.getRatingNum(), actual.getRatingNum());
       assertEquals(expected.getBathroom(), actual.getBathroom());
       assertEquals(expected.getBedroom(), actual.getBedroom());
       assertEquals(expected.getKitchen(), actual.getKitchen());
       assertEquals(expected.getDescription(), actual.getDescription());
       assertEquals(expected.getLocation(), actual.getLocation());
       assertEquals(expected.getPricePerNight(), actual.getPricePerNight());
       assertEquals(expected.getPropertyType(), actual.getPropertyType());
    }

    @Test
    public void shouldReturnManagedPropertyResponseWhenUpdateProperty() {
        ManagedPropertyResponse expected = ManagedPropertyResponse.builder()
                .id(3)
                .name("Nha ben bien 2")
                .description("The Big Pine House is located right on the hillside, the surrounding old pine forest brings a cool vibe all year round for Homestay. The house also owns an extremely spacious front view when the brick wall is replaced by transparent toughened glass wall, without visibility restrictions.")
                .avgRating(new BigDecimal("4.4"))
                .ratingNum(11)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .pricePerNight(new BigDecimal("101"))
                .location(LocationResponse.builder().id(5).state("Vietnam").city("Hanoi").details("Minh Phu").description(null).build())
                .propertyImages(images3)
                .propertyType(TypeResponse.builder().id(3).name("villa").build())
                .build();

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(new Amenity(2, "beach facing", null));

        PropertyImage image9 = PropertyImage.builder().id(9).name("property5-1.jpg").build();
        PropertyImage image10 = PropertyImage.builder().id(10).name("property5-2.jpg").build();
        PropertyImage image11 = PropertyImage.builder().id(11).name("property5-3.jpg").build();
        List<PropertyImage> images3 = new ArrayList<>();
        images3.add(image9);
        images3.add(image10);
        images3.add(image11);

        ManagedPropertyRequest request = ManagedPropertyRequest.builder()
                .name("Nha ben bien 2")
                .avgRating(new BigDecimal("4.4"))
                .ratingNum(11)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .description("The Big Pine House is located right on the hillside, the surrounding old pine forest brings a cool vibe all year round for Homestay. The house also owns an extremely spacious front view when the brick wall is replaced by transparent toughened glass wall, without visibility restrictions.")
                .pricePerNight(new BigDecimal("101"))
                .amenities(amenities)
                .location(Location.builder().id(5).state("Vietnam").city("Hanoi").details("Minh Phu").description(null).build())
                .propertyImages(images3)
                .propertyType(PropertyType.builder().id(3).name("villa").build())
                .user(Users.builder().id(5).email("vietanh@gmail.com").firstName("Anh").lastName("Viet").username("Viet Anh").phone("0333033094").role(Role.builder().id(1).name("owner").build()).build())
                .build();

        ManagedPropertyResponse actual = propertyService.update(3, request);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAvgRating(), actual.getAvgRating());
        assertEquals(expected.getRatingNum(), actual.getRatingNum());
        assertEquals(expected.getBathroom(), actual.getBathroom());
        assertEquals(expected.getBedroom(), actual.getBedroom());
        assertEquals(expected.getKitchen(), actual.getKitchen());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getLocation(), actual.getLocation());
        assertEquals(expected.getPricePerNight(), actual.getPricePerNight());
        assertEquals(expected.getPropertyType(), actual.getPropertyType());
    }

    @Transactional
    @Test
    public void shouldDoNothingWhenDeleteProperty() {
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(new Amenity(1, "pool facing", null));
        amenities.add(new Amenity(2, "beach facing", null));
        List<PropertyImage> images = new ArrayList<>();
        images.add(PropertyImage.builder().name("image.jpg").build());
        ManagedPropertyRequest request = ManagedPropertyRequest.builder()
                .name("Nha ben ho rrwer")
                .avgRating(new BigDecimal("0"))
                .ratingNum(0)
                .bathroom(1)
                .bedroom(1)
                .kitchen(1)
                .description("A beautiful house")
                .pricePerNight(new BigDecimal("50"))
                .propertyImages(images)
                .amenities(amenities)
                .propertyType(PropertyType.builder().id(1).name("flat").build())
                .location(Location.builder().state("Vietnam").city("Hanoi").details("100 Nguyen Trai").build())
                .user(Users.builder().id(5).email("vietanh@gmail.com").firstName("Anh").lastName("Viet").username("Viet Anh").phone("0333033094").role(Role.builder().id(1).name("owner").build()).build())
                .build();

        ManagedPropertyResponse response = propertyService.save(request);
       propertyService.delete(response.getId());
       assertFalse(propertyRepository.existsById(response.getId()));
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenIdNotExisted() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.delete(25);
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }

    @Test
    public void shouldReturnPropertyIfIdExisted() {
        assertNotNull(propertyService.handlePropertyNotFoundException(2));
    }

    @Test
    public void shouldThrowExceptionIfPropertyNotExisted() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.handlePropertyNotFoundException(25);
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }


    @Test
    public void shouldThrowResourceExistedExceptionIfPropertyExistedWhenAddNew() {
        ResourceExistedException ex = Assertions.assertThrows(ResourceExistedException.class, () -> {
            propertyService.handleExistedPropertyByNameException("Nha Ben Run(U Lesa) - Big Pine House");
        }, Constants.RESOURCE_ALREADY_EXISTED);
        Assertions.assertEquals(Constants.RESOURCE_ALREADY_EXISTED, ex.getMessage());
    }

    @Test
    @Transactional
    public void shouldDoNothingWhenUploadImageForProperty() {
        propertyService.uploadImage("upload_image.jpg", 1);
    }

@Test
@Transactional
    public void shouldReturnPropertyDetailedIfPropertyExisted() {
        List<ImageResponse> images = new ArrayList<>();
        images.add(ImageResponse.builder().id(1).name("property1-1.jpg").build());
        images.add(ImageResponse.builder().id(2).name("property1-2.jpg").build());
        images.add(ImageResponse.builder().id(3).name("property1-3.jpg").build());
        images.add(ImageResponse.builder().id(4).name("property1-4.jpg").build());
        images.add(ImageResponse.builder().id(5).name("property1-5.jpg").build());

        List<AmenityResponse> amenityResponses = new ArrayList<>();
        amenityResponses.add(AmenityResponse.builder().id(1).name("pool facing").build());
        amenityResponses.add(AmenityResponse.builder().id(2).name("beach facing").build());

        PropertyDetailedResponse expected = PropertyDetailedResponse.builder()
                .id(1)
                .name("Nha Ben Run(U Lesa) - Big Pine House")
                .avgRating(new BigDecimal("4.5"))
                .ratingNum(11)
                .description("The Big Pine House is located right on the hillside, the surrounding old pine forest brings a cool vibe all year round for Homestay. The house also owns an extremely spacious front view when the brick wall is replaced by transparent toughened glass wall, without visibility restrictions.")
                .pricePerNight(new BigDecimal("101"))
                .bedroom(1)
                .bathroom(1)
                .kitchen(1)
                .ownerEmail("owner@gmail.com")
                .ownerName("owner")
                .ownerId(2)
                .propertyImages(images)
                .typeName("villa")
                .amenities(amenityResponses)
                .location(LocationResponse.builder().id(1).state("Vietnam").city("Tp.HCM").details("Thanhxuan street").description(null).build())
                .reservationStatus(-1)
                .currentReservations(new ArrayList<>())
                .build();

        PropertyDetailedResponse actual = propertyService.getPropertyDto(1, 1);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getBathroom(), actual.getBathroom());
        assertEquals(expected.getBedroom(), actual.getBathroom());
        assertEquals(expected.getKitchen(), actual.getKitchen());
        assertEquals(expected.getLocation(), actual.getLocation());
        assertEquals(expected.getAvgRating(), actual.getAvgRating());
        assertEquals(expected.getRatingNum(), actual.getRatingNum());
        assertEquals(expected.getPropertyImages().size(), actual.getPropertyImages().size());
        assertEquals(expected.getPropertyImages().get(0).getName(), actual.getPropertyImages().get(0).getName());
        assertEquals(expected.getCurrentReservations().size(), actual.getCurrentReservations().size());
        assertEquals(expected.getOwnerId(), actual.getOwnerId());
        assertEquals(expected.getOwnerName(), actual.getOwnerName());
        assertEquals(expected.getOwnerEmail(), actual.getOwnerEmail());
        assertEquals(expected.getPricePerNight(), actual.getPricePerNight());
        assertEquals(expected.getReservationStatus(), expected.getReservationStatus());
        assertEquals(expected.getTypeName(), actual.getTypeName());
        assertEquals(expected.getAmenities().size(), actual.getAmenities().size());

}

@Transactional
@Test
public void shouldReturnPropertyWhenSearchingWithName() {
        Map<String, Object> actual = propertyService.searchProperty("Nha Ben Run(U Lesa) - Big Pine House", 0, 10, "name");
         List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
        assertEquals(1, actualList.size());
        assertEquals(0, actual.get("currentPage"));
        assertEquals(1, actual.get("totalPages"));
        assertEquals(1L, actual.get("itemsNumber"));
        assertEquals(response1.getId(), actualList.get(0).getId());
        assertEquals(response1.getName(), actualList.get(0).getName());
}

@Transactional
@Test
public void shouldReturnPropertiesWhenSearchingWithPrice() {
        Map<String, Object> actual = propertyService.searchProperty("101", 0, 10, "name");
    List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
    assertEquals(2, actualList.size());
    assertEquals(0, actual.get("currentPage"));
    assertEquals(1, actual.get("totalPages"));
    assertEquals(2L, actual.get("itemsNumber"));
    assertEquals(response1.getId(), actualList.get(0).getId());
    assertEquals(response1.getName(), actualList.get(0).getName());
    assertEquals(response3.getId(), actualList.get(1).getId());
    assertEquals(response3.getName(), actualList.get(1).getName());
}

@Test
public void shouldReturnAllPropertiesInDb() {
        Map<String, Object> actual = propertyServiceImpl.getAllPropertyResponse(0, 10, "name");
    List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
        assertEquals(4, actual.size());
        assertEquals(4, actualList.size());
}

@Test
public void shouldReturnEmptyMapWhenSearchingWithNonExistedAttribute() {
    Map<String, Object> actual = propertyService.searchProperty("rwreiio", 0, 10, "name");
    assertTrue(actual.isEmpty());
}
//
//@Transactional
//@Test
//    public void shouldReturnPropertyMapWhenSearchingWithLocationOnly() {
//    Map<String, Object> actual = propertyService.searchPropertyByCriteria(
//            SearchCriteriaRequest.builder()
//                    .location("Hanoi")
//                    .type(Constants.DEFAULT_TYPE_VALUE)
//                    .amenity(Constants.DEFAULT_AMENITY_VALUE)
//                    .checkInDate(LocalDate.now().atTime(LocalDateTime.now().getHour() - 7, 0, 0))
//                    .checkoutDate(LocalDate.now().atTime(LocalDateTime.now().getHour() - 7, 0, 0))
//                    .build(),
//            0, 10, "name"
//    );
//logger.info("map " + actual);
//    Map<String, Object> expected = new HashMap<>();
//    List<PropertyResponse> responses = new ArrayList<>();
//    responses.add(response2);
//    responses.add(response3);
//    expected.put("data", responses);
//    expected.put("currentPage", 0);
//    expected.put("totalPages", 1);
//    expected.put("itemsNumber", 1);
//    assertEquals(expected.size(), actual.size());
//    List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
//    assertEquals(responses.size(), actualList.size());
//    assertEquals(responses.get(0).getId(), actualList.get(0).getId());
//    assertEquals(responses.get(1).getId(), actualList.get(1).getId());
//    }
//
//    @Transactional
//    @Test
//    public void shouldReturnPropertyMapWhenSearchingWithPropertyTypeOnly() {
//        Map<String, Object> actual = propertyService.searchPropertyByCriteria(
//                SearchCriteriaRequest.builder()
//                        .location(Constants.DEFAULT_LOCATION_VALUE)
//                        .type("apartment")
//                        .amenity(Constants.DEFAULT_AMENITY_VALUE)
//                        .checkInDate(LocalDate.now().atTime(LocalDateTime.now().getHour() - 7, 0, 0))
//                        .checkoutDate(LocalDate.now().atTime(LocalDateTime.now().getHour() - 7, 0, 0))
//                        .build(),
//                0, 10, "name"
//        );
//        List<PropertyResponse> responses = new ArrayList<>();
//        responses.add(response2);
//        Map<String, Object> expected = new HashMap<>();
//        expected.put("data", responses);
//        expected.put("currentPage", 0);
//        expected.put("totalPages", 1);
//        expected.put("itemsNumber", 1);
//        assertEquals(expected.size(), actual.size());
//        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
//        assertEquals(responses.size(), actualList.size());
//        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
//    }
//
//
//
//    @Transactional
//    @Test
//    public void shouldReturnPropertyMapWhenSearchingWithValidCheckInAndCheckoutDate() {
//        Map<String, Object> actual = propertyService.searchPropertyByCriteria(
//                SearchCriteriaRequest.builder()
//                        .location(Constants.DEFAULT_LOCATION_VALUE)
//                        .type(Constants.DEFAULT_TYPE_VALUE)
//                        .amenity(Constants.DEFAULT_AMENITY_VALUE)
//                        .checkInDate(LocalDateTime.of(2022, 6, 29, 12, 0, 0))
//                        .checkoutDate(LocalDateTime.of(2022, 7, 7, 12, 0, 0))
//                        .build(),
//                0, 10, "name"
//        );
//
//        Map<String, Object> expected = new HashMap<>();
//        List<PropertyResponse> responses = new ArrayList<>();
//        responses.add(response1);
//        expected.put("data", responses);
//        expected.put("currentPage", 0);
//        expected.put("totalPages", 1);
//        expected.put("itemsNumber", 1);
//        assertEquals(expected.size(), actual.size());
//        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
//        assertEquals(responses.size(), actualList.size());
//        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
//    }
//
//    @Transactional
//    @Test
//    public void shouldReturnPropertyMapWhenSearchingWithAmenity() {
//        Map<String, Object> actual = propertyService.searchPropertyByCriteria(
//                SearchCriteriaRequest.builder()
//                        .location(Constants.DEFAULT_LOCATION_VALUE)
//                        .type(Constants.DEFAULT_TYPE_VALUE)
//                        .amenity("pool facing")
//                        .checkInDate(LocalDate.now().atTime(LocalDateTime.now().getHour() - 7, 0, 0))
//                        .checkoutDate(LocalDate.now().atTime(LocalDateTime.now().getHour() - 7, 0, 0))
//                        .build(),
//                0, 10, "name"
//        );
//
//        Map<String, Object> expected = new HashMap<>();
//        List<PropertyResponse> responses = new ArrayList<>();
//        responses.add(response1);
//        expected.put("data", responses);
//        expected.put("currentPage", 0);
//        expected.put("totalPages", 1);
//        expected.put("itemsNumber", 1);
//        assertEquals(expected.size(), actual.size());
//        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
//        assertEquals(responses.size(), actualList.size());
//        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
//    }
//
//    @Transactional
//    @Test
//    public void shouldReturnPropertyMapWithAllCriteria() {
//        Map<String, Object> actual = propertyService.searchPropertyByCriteria(
//                SearchCriteriaRequest.builder()
//                        .location("Tp.HCM")
//                        .type("villa")
//                        .amenity("pool facing")
//                        .checkInDate(LocalDateTime.of(2022, 6, 29, 12, 0, 0))
//                        .checkoutDate(LocalDateTime.of(2022, 7, 7, 12, 0, 0))
//                        .build(),
//                0, 10, "name"
//        );
//
//        Map<String, Object> expected = new HashMap<>();
//        List<PropertyResponse> responses = new ArrayList<>();
//        responses.add(response1);
//        expected.put("data", responses);
//        expected.put("currentPage", 0);
//        expected.put("totalPages", 1);
//        expected.put("itemsNumber", 1);
//        assertEquals(expected.size(), actual.size());
//        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
//        assertEquals(responses.size(), actualList.size());
//        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
//    }
//
//    @Test
//    public void shouldThrowInvalidDateRangeExceptionWhenCheckInDateAfterCheckoutDate() {
//        InvalidDateRangeException ex = Assertions.assertThrows(InvalidDateRangeException.class, () -> {
//            propertyService.searchPropertyByCriteria(
//                    SearchCriteriaRequest.builder()
//                            .location("Tp.HCM")
//                            .type("villa")
//                            .amenity("pool facing")
//                            .checkInDate(LocalDateTime.of(2022, 6, 29, 12, 0, 0))
//                            .checkoutDate(LocalDateTime.of(2022, 6, 7, 12, 0, 0))
//                            .build(),
//                    0, 10, "startDate"
//            );
//        }, Constants.INVALID_DATE_RANGE);
//        Assertions.assertEquals(Constants.INVALID_DATE_RANGE, ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowInvalidDateRangeExceptionWhenCheckInDateBeforeCurrentDate() {
//        InvalidDateRangeException ex = Assertions.assertThrows(InvalidDateRangeException.class, () -> {
//            propertyService.searchPropertyByCriteria(
//                    SearchCriteriaRequest.builder()
//                            .location("Tp.HCM")
//                            .type("villa")
//                            .amenity("pool facing")
//                            .checkInDate(LocalDateTime.of(2022, 3, 29, 12, 0, 0))
//                            .checkoutDate(LocalDateTime.of(2022, 6, 7, 12, 0, 0))
//                            .build(),
//                    0, 10, "startDate"
//            );
//        }, Constants.INVALID_DATE_RANGE);
//        Assertions.assertEquals(Constants.INVALID_DATE_RANGE, ex.getMessage());
//    }
//
//    @Test
//    public void shouldThrowInvalidDateRangeExceptionWhenCheckoutDateBeforeCurrentDate() {
//        InvalidDateRangeException ex = Assertions.assertThrows(InvalidDateRangeException.class, () -> {
//            propertyService.searchPropertyByCriteria(
//                    SearchCriteriaRequest.builder()
//                            .location("Tp.HCM")
//                            .type("villa")
//                            .amenity("pool facing")
//                            .checkInDate(LocalDateTime.of(2022, 6, 29, 12, 0, 0))
//                            .checkoutDate(LocalDateTime.of(2022, 3, 7, 12, 0, 0))
//                            .build(),
//                    0, 10, "startDate"
//            );
//        }, Constants.INVALID_DATE_RANGE);
//        Assertions.assertEquals(Constants.INVALID_DATE_RANGE, ex.getMessage());
//    }

    @Transactional
    @Test
    public void shouldReturnAllProperties() {
        List<PropertyResponse> responses = new ArrayList<>();
        responses.add(response2);
        responses.add(response1);
        responses.add(response3);
        Map<String, Object> expected = new HashMap<>();
        expected.put("data", responses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 1);
        expected.put("itemsNumber", 3);
        Map<String, Object> actual = propertyService.getAllPropertyDto(0, 10, "name");
        assertEquals(expected.size(), actual.size());
        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
        assertEquals(responses.size() + 1, actualList.size());
        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
        assertEquals(responses.get(1).getId(), actualList.get(1).getId());
        assertEquals(responses.get(2).getId(), actualList.get(2).getId());
    }

    @Transactional
    @Test
    public void shouldReturnAllUnreservedProperty() {
        List<PropertyResponse> responses = new ArrayList<>();
        responses.add(response1);
        Map<String, Object> expected = new HashMap<>();
        expected.put("data", responses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 1);
        expected.put("itemsNumber", 1);
        Map<String, Object> actual = propertyService.getAvailablePropertiesDto(1, 0, 10, "name");
        assertEquals(expected.size(), actual.size());
        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
        assertEquals(responses.size() + 1, actualList.size());
        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
    }

    @Transactional
    @Test
    public void shouldReturnAllPropertyWhenUserHasNoCurrentlyReservedProperty() {
        List<PropertyResponse> responses = new ArrayList<>();
        responses.add(response2);
        responses.add(response1);
        responses.add(response3);
        Map<String, Object> expected = new HashMap<>();
        expected.put("data", responses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 1);
        expected.put("itemsNumber", 3);
        Map<String, Object> actual = propertyService.getAvailablePropertiesDto(3, 0, 10, "name");
        assertEquals(expected.size(), actual.size());
        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
        assertEquals(responses.size() + 1, actualList.size());
        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
        assertEquals(responses.get(1).getId(), actualList.get(1).getId());
        assertEquals(responses.get(2).getId(), actualList.get(2).getId());
    }

    @Transactional
    @Test
    public void shouldReturnCurrentlyReservedProperties() {
        List<PropertyResponse> responses = new ArrayList<>();
        responses.add(response2);
        responses.add(response3);
        Map<String, Object> expected = new HashMap<>();
        expected.put("data", responses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 1);
        expected.put("itemsNumber", 2);
        Map<String, Object> actual = propertyService.getCustomerPropertiesDto(1, 0, 10, "name");
        assertEquals(expected.size(), actual.size());
        List<PropertyResponse> actualList = (List<PropertyResponse>) actual.get("data");
        assertEquals(responses.size(), actualList.size());
        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
        assertEquals(responses.get(1).getId(), actualList.get(1).getId());
    }

    @Transactional
    @Test
    public void shouldReturnManagedPropertiesOfOwner() {
        List<ManagedPropertyResponse> responses = new ArrayList<>();
        responses.add(managedPropertyResponse3);
        Map<String, Object> expected = new HashMap<>();
        expected.put("data", responses);
        expected.put("currentPage", 0);
        expected.put("totalPages", 1);
        expected.put("itemsNumber", 1);
        Map<String, Object> actual = propertyService.findAllPropertyOfOwner(5, 0, 10, "name");
        assertEquals(expected.size(), actual.size());
        List<ManagedPropertyResponse> actualList = (List<ManagedPropertyResponse>) actual.get("data");
        assertEquals(responses.size(), actualList.size());
        assertEquals(responses.get(0).getId(), actualList.get(0).getId());
    }

    @Test
    public void shouldReturnEmptyManagedPropertyListOfOwner() {
        Map<String, Object> actual = propertyService.findAllPropertyOfOwner(6, 0, 10, "name");
        List<ManagedPropertyResponse> actualList = (List<ManagedPropertyResponse>) actual.get("data");
        assertEquals(actual.size(), 0);
    }

    @Transactional
    @Test
    public void shouldReturnAllImagesOfAProperty() {
        PropertyImagesResponse expected = PropertyImagesResponse.builder().propertyId(2)
                        .propertyName("New Studio, lift, Hoan Kiem, near old quarter #B01")
                                .propertyImages(images2).build();
        PropertyImagesResponse actual = propertyService.findImagesOfProperty(2);
        assertEquals(expected.getPropertyId(), actual.getPropertyId());
        assertEquals(expected.getPropertyName(), actual.getPropertyName());
        assertEquals(expected.getPropertyImages().size(), actual.getPropertyImages().size());
    }

    @Test
    public void shouldDoNothingWhenDeleteImageOfProperty() {
//            propertyService.deleteImageOfProperty(5);
    }

    @Test
    public void shouldThrowExceptionWhenImageOfPropertyNotExisted() {
        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            propertyService.deleteImageOfProperty(25);
        }, Constants.RESOURCE_NOT_FOUND);
        Assertions.assertEquals(Constants.RESOURCE_NOT_FOUND, ex.getMessage());
    }






}
