package com.by.hotels.controllers;

import com.by.hotels.dto.HistogramDto;
import com.by.hotels.dto.HotelCreationDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/property-view")
@Tag(name = "Hotel Controller", description = "API for managing hotels and amenities")
public class HotelController {

    private static final Logger log = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    private HotelService hotelService;

    @Operation(
            summary = "Get all hotels",
            description = "Retrieves a list of all hotels with brief details."
    )
    @ApiResponse(responseCode = "200", description = "List of hotels found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HotelOverviewDto.class)))
    @ApiResponse(responseCode = "404", description = "No hotels found")
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelOverviewDto>> getHotels() {
        List<HotelOverviewDto> hotels = hotelService.getAll();
        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @Operation(
            summary = "Get hotel by ID",
            description = "Retrieves detailed information about a hotel by its ID."
    )
    @ApiResponse(responseCode = "200", description = "Hotel found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDto.class)))
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDto> getHotel(
            @Parameter(description = "Hotel ID", required = true)
            @PathVariable Long id) {
        HotelDto hotelDto = hotelService.getById(id);
        return ResponseEntity.ok(hotelDto);
    }

    @Operation(
            summary = "Search hotels",
            description = "Search for hotels by various fields."
    )
    @ApiResponse(responseCode = "200", description = "Hotels found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelOverviewDto.class)))
    @ApiResponse(responseCode = "404", description = "No hotels found")
    @GetMapping("/search")
    public ResponseEntity<List<HotelOverviewDto>> getHotelsByField(
            @Parameter(description = "Hotel name") @RequestParam(required = false) String name,
            @Parameter(description = "Hotel description") @RequestParam(required = false) String description,
            @Parameter(description = "Hotel brand") @RequestParam(required = false) String brand,
            @Parameter(description = "House number") @RequestParam(required = false) String houseNumber,
            @Parameter(description = "Street") @RequestParam(required = false) String street,
            @Parameter(description = "City") @RequestParam(required = false) String city,
            @Parameter(description = "County") @RequestParam(required = false) String county,
            @Parameter(description = "Post code") @RequestParam(required = false) String postCode,
            @Parameter(description = "Phone") @RequestParam(required = false) String phone,
            @Parameter(description = "Email") @RequestParam(required = false) String email,
            @Parameter(description = "Amenities") @RequestParam(required = false) Set<String> amenities,
            @Parameter(description = "Check-in time") @RequestParam(required = false) String checkIn,
            @Parameter(description = "Check-out time") @RequestParam(required = false) String checkOut) {

        List<HotelOverviewDto> hotels = hotelService.searchHotels(name, description, brand, houseNumber,
                street, city, county, postCode, phone, email, amenities, checkIn, checkOut);
        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @Operation(
            summary = "Create hotel",
            description = "Creates a new hotel using the provided data."
    )
    @ApiResponse(responseCode = "201", description = "Hotel created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelOverviewDto.class)))
    @PostMapping("/hotels")
    public ResponseEntity<HotelOverviewDto> create(
            @Parameter(description = "Hotel creation data", required = true) @RequestBody HotelCreationDto hotelCreationDto) {
        HotelOverviewDto createdHotel = hotelService.create(hotelCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @Operation(
            summary = "Add amenities to hotel",
            description = "Adds the provided amenities to the hotel identified by its ID."
    )
    @ApiResponse(responseCode = "200", description = "Amenities added successfully")
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(
            @Parameter(description = "Set of new amenities", required = true) @RequestBody Set<String> newAmenities,
            @Parameter(description = "Hotel ID", required = true) @PathVariable Long id) {
        hotelService.addAmenities(id, newAmenities);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get histogram data",
            description = "Retrieves histogram data based on the specified parameter."
    )
    @ApiResponse(responseCode = "200", description = "Histogram data retrieved",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HistogramDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid parameter")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/histogram/{param}")
    public ResponseEntity<HistogramDto> getHistogram(
            @Parameter(description = "Parameter for histogram", required = true)
            @PathVariable String param) {
        try {
            log.info("Received parameter: {}", param);
            HistogramDto dto = hotelService.getHistogramData(param);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            log.error("Invalid parameter: {}", param);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Internal error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
