package com.by.hotels.services;

import com.by.hotels.convertors.ConvertDtoToHotel;
import com.by.hotels.convertors.ConvertHotelToHotelDetailedDto;
import com.by.hotels.convertors.ConvertHotelToHotelOverviewDto;
import com.by.hotels.dto.HistogramDto;
import com.by.hotels.dto.HotelCreationDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.entities.Amenities;
import com.by.hotels.entities.Hotel;
import com.by.hotels.models.Address;
import com.by.hotels.models.ArrivalTime;
import com.by.hotels.models.Contacts;
import com.by.hotels.repositories.AmenitiesRepository;
import com.by.hotels.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AmenitiesRepository amenitiesRepository;

    @Mock
    private ConvertDtoToHotel convertorToHotel;

    @Mock
    private ConvertHotelToHotelOverviewDto convertorToOverviewDto;

    @Mock
    private ConvertHotelToHotelDetailedDto convertorToDetailedDto;

    @InjectMocks
    private HotelService hotelService;

    private Hotel testHotel;
    private Amenities testAmenity;

    @BeforeEach
    void setUp() {
        testAmenity = new Amenities("Wi-Fi");
        testAmenity.setId(1L);

        Address address = new Address();
        address.setHouseNumber("123");
        address.setStreet("Main St");
        address.setCity("New York");
        address.setCounty("NY");
        address.setPostCode("10001");

        Contacts contacts = new Contacts();
        contacts.setPhone("+1234567890");
        contacts.setEmail("test@example.com");

        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("14:00");
        arrivalTime.setCheckOut("12:00");

        testHotel = new Hotel();
        testHotel.setId(1L);
        testHotel.setName("Test Hotel");
        testHotel.setBrand("Test Brand");
        testHotel.setDescription("Test Description");
        testHotel.setAddress(address);
        testHotel.setContacts(contacts);
        testHotel.setArrivalTime(arrivalTime);
        testHotel.setAmenitiesList(new HashSet<>(Collections.singletonList(testAmenity)));
    }

    @Test
    void getAll_ShouldReturnListOfOverviewDtos() {
        when(hotelRepository.findAll()).thenReturn(Collections.singletonList(testHotel));
        when(convertorToOverviewDto.convert(testHotel)).thenReturn(new HotelOverviewDto.Builder()
                .id(1L)
                .name("Test Hotel")
                .build());

        List<HotelOverviewDto> result = hotelService.getAll();

        assertEquals(1, result.size());
        verify(hotelRepository).findAll();
    }

    @Test
    void getById_WhenHotelExists_ShouldReturnDetailedDto() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(testHotel));
        when(convertorToDetailedDto.convert(testHotel)).thenReturn(new HotelDto.Builder().build());

        HotelDto result = hotelService.getById(1L);

        assertNotNull(result);
        verify(hotelRepository).findById(1L);
    }

    @Test
    void getById_WhenHotelNotExists_ShouldThrowException() {
        when(hotelRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hotelService.getById(2L));
    }

    @Test
    void searchHotels_WithNameFilter_ShouldReturnFilteredResults() {
        when(hotelRepository.findAll()).thenReturn(Collections.singletonList(testHotel));
        when(convertorToOverviewDto.convert(testHotel)).thenReturn(new HotelOverviewDto.Builder().build());

        List<HotelOverviewDto> result = hotelService.searchHotels("Test Hotel", null, null, null, null, null, null, null, null, null, null, null, null);

        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldSaveHotelAndReturnOverviewDto() {
        HotelCreationDto creationDto = new HotelCreationDto.Builder().build();
        when(convertorToHotel.convert(creationDto)).thenReturn(testHotel);
        when(hotelRepository.save(testHotel)).thenReturn(testHotel);
        when(convertorToOverviewDto.convert(testHotel)).thenReturn(new HotelOverviewDto.Builder().build());

        HotelOverviewDto result = hotelService.create(creationDto);

        assertNotNull(result);
        verify(hotelRepository).save(testHotel);
    }

    @Test
    void addAmenities_ShouldAddNewAndExistingAmenities() {

        Set<String> newAmenities = Set.of("Wi-Fi", "Pool", "Spa");


        Amenities poolAmenity = new Amenities("Pool");
        Amenities spaAmenity = new Amenities("Spa");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(testHotel));
        when(amenitiesRepository.findByName("Pool")).thenReturn(Optional.of(poolAmenity));
        when(amenitiesRepository.findByName("Spa")).thenReturn(Optional.empty());
        when(amenitiesRepository.save(argThat(a -> a.getName().equals("Spa")))).thenReturn(spaAmenity);

        hotelService.addAmenities(1L, newAmenities);


        verify(amenitiesRepository, times(2)).findByName(anyString());
        verify(amenitiesRepository).save(argThat(a ->
                a.getName().equals("Spa") && a.getId() == null
        ));

        assertThat(testHotel.getAmenitiesList())
                .isNotNull() // Проверяем, что список не null
                .extracting(Amenities::getName)
                .doesNotContainNull() // Гарантируем отсутствие null в именах
                .containsExactlyInAnyOrder("Wi-Fi", "Pool", "Spa");
    }

    @Test
    void getHistogramData_ForBrand_ShouldReturnCorrectData() {
        List<Object[]> mockData = List.of(new Object[]{"Brand1", 5L}, new Object[]{"Brand2", 3L});
        when(hotelRepository.countHotelsByBrand()).thenReturn(mockData);

        HistogramDto result = hotelService.getHistogramData("brand");

        assertEquals(2, result.getData().size());
        assertEquals(5L, result.getData().get("Brand1"));
    }

    @Test
    void getHistogramData_WithInvalidParam_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> hotelService.getHistogramData("invalid"));
    }
}