package com.by.hotels;


import com.by.hotels.convertors.ConvertHotelToHotelDetailedDto;
import com.by.hotels.convertors.ConvertHotelToHotelOverviewDto;
import com.by.hotels.dto.HistogramDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.entities.Amenities;
import com.by.hotels.entities.Hotel;
import com.by.hotels.models.Address;
import com.by.hotels.repositories.AmenitiesRepository;
import com.by.hotels.repositories.HotelRepository;
import com.by.hotels.services.HotelService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTests {


    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private AmenitiesRepository amenitiesRepository;

    @Mock
    private ConvertHotelToHotelOverviewDto convertorToOverviewDto;

    @Mock
    private ConvertHotelToHotelDetailedDto convertorToDetailedDto;
    @Mock
    private Hotel hotel;
    @InjectMocks
    private HotelService hotelService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(hotel.getId()).thenReturn(1L);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
    }


    @Test
    void testGetAll() {

        Hotel hotel = new Hotel();
        HotelOverviewDto overviewDto = new HotelOverviewDto.Builder()
                .id(1L)
                .name("Test Hotel")
                .description("Test Description")
                .address("Test Address")
                .phone("1234567890")
                .build();

        when(hotelRepository.findAll()).thenReturn(Collections.singletonList(hotel));
        when(convertorToOverviewDto.convert(hotel)).thenReturn(overviewDto);
        List<HotelOverviewDto> result = hotelService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(overviewDto, result.get(0));
        verify(hotelRepository, times(1)).findAll();
        verify(convertorToOverviewDto, times(1)).convert(hotel);
    }


    @Test
    void testGetById() {

        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        HotelDto hotelDto = new HotelDto.Builder()
                .id(hotelId)
                .name("Test Hotel")
                .brand("Test Brand")
                .description("Test Description")
                .address(new Address())
                .phone("1234567890")
                .email("test@example.com")
                .checkIn("14:00")
                .checkOut("12:00")
                .amenities(Collections.emptySet())
                .build();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(convertorToDetailedDto.convert(hotel)).thenReturn(hotelDto);


        HotelDto result = hotelService.getById(hotelId);

        assertNotNull(result);
        assertEquals(hotelDto, result);
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(convertorToDetailedDto, times(1)).convert(hotel);
    }

    @Test
    void testGetById_NotFound() {

        Long hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hotelService.getById(hotelId));
        verify(hotelRepository, times(1)).findById(hotelId);
    }


    @Test
    void testGetHistogramData_Brand() {

        String param = "brand";
        List<Object[]> results = List.of(new Object[]{"Brand1", 5L}, new Object[]{"Brand2", 3L});
        when(hotelRepository.countHotelsByBrand()).thenReturn(results);
        HistogramDto result = hotelService.getHistogramData(param);
        assertNotNull(result);
        assertEquals(2, result.getData().size());
        assertEquals(5L, result.getData().get("Brand1"));
        assertEquals(3L, result.getData().get("Brand2"));
        verify(hotelRepository, times(1)).countHotelsByBrand();
    }

    @Test
    void testGetHistogramData_InvalidParam() {
        String param = "invalid";
        assertThrows(IllegalArgumentException.class, () -> hotelService.getHistogramData(param));
    }

    @Test
    void testAddAmenities() {

        when(amenitiesRepository.findByName("WiFi")).thenReturn(Optional.empty());
        when(amenitiesRepository.findByName("Pool")).thenReturn(Optional.empty());


        when(amenitiesRepository.save(any(Amenities.class))).thenAnswer(invocation -> {
            Amenities amenities = invocation.getArgument(0);
            return new Amenities(amenities.getName());
        });


        Set<String> amenitiesSet = new HashSet<>(Arrays.asList("WiFi", "Pool"));
        when(hotel.getId()).thenReturn(1L);
        Long hotelId = hotel.getId();
        hotelService.addAmenities(hotelId, amenitiesSet);
        verify(amenitiesRepository).save(argThat(amenities -> "WiFi".equals(amenities.getName())));
        verify(amenitiesRepository).save(argThat(amenities -> "Pool".equals(amenities.getName())));
        verify(amenitiesRepository).findByName("WiFi");
        verify(amenitiesRepository).findByName("Pool");
    }

}

