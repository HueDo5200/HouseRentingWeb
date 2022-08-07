package com.self.house.renting.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaRequest {
    @NotNull
    private String location;
    @NotNull
    private String type;
//    @NotNull
//    private String amenity;
   List<Integer> amenityIds;
    @NotNull
    private LocalDateTime checkInDate;
    @NotNull
    private LocalDateTime checkoutDate;
}
