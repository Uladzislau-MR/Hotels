package com.by.hotels.services;


import com.by.hotels.convertors.ConvertDtoToHotel;
import com.by.hotels.convertors.ConvertHotelToHotelDetailedDto;
import com.by.hotels.convertors.ConvertHotelToHotelOverviewDto;
import com.by.hotels.dto.HistogramDto;
import com.by.hotels.dto.HotelCreationDto;
import com.by.hotels.dto.HotelDto;
import com.by.hotels.dto.HotelOverviewDto;
import com.by.hotels.entities.Hotel;
import com.by.hotels.entities.Amenities;
import com.by.hotels.repositories.AmenitiesRepository;
import com.by.hotels.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final ConvertDtoToHotel convertorToHotel;
    private final ConvertHotelToHotelOverviewDto convertorToOverviewDto;
    private final ConvertHotelToHotelDetailedDto convertorToDetailedDto;


    @Autowired
    public HotelService(HotelRepository hotelRepository, AmenitiesRepository amenitiesRepository,
                        ConvertDtoToHotel convertorToHotel,
                        ConvertHotelToHotelOverviewDto convertorToOverviewDto,
                        ConvertHotelToHotelDetailedDto convertorToDetailedDto) {
        this.hotelRepository = hotelRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.convertorToHotel = convertorToHotel;
        this.convertorToOverviewDto = convertorToOverviewDto;
        this.convertorToDetailedDto = convertorToDetailedDto;

    }

    public List<HotelOverviewDto> getAll() {
        return hotelRepository.findAll().stream()
                .map(convertorToOverviewDto::convert)
                .collect(Collectors.toList());
    }

    public HotelDto getById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id " + id));
        return convertorToDetailedDto.convert(hotel);
    }

    public List<HotelOverviewDto> searchHotels(String name, String description, String brand, String houseNumber,
                                               String street, String city, String county,
                                               String postCode, String phone, String email, Set<String> amenities,
                                               String checkIn, String checkOut) {

        return hotelRepository.findAll().stream()
                .filter(hotel -> (name == null || hotel.getName().equalsIgnoreCase(name)) &&
                                 (description == null || hotel.getDescription().equalsIgnoreCase(description)) &&
                                 (brand == null || hotel.getBrand().equalsIgnoreCase(brand)) &&
                                 (houseNumber == null || hotel.getAddress().getHouseNumber().equalsIgnoreCase(houseNumber)) &&
                                 (street == null || hotel.getAddress().getStreet().equalsIgnoreCase(street)) &&
                                 (county == null || hotel.getAddress().getCounty().equalsIgnoreCase(county)) &&
                                 (postCode == null || hotel.getAddress().getPostCode().equalsIgnoreCase(postCode)) &&
                                 (city == null || hotel.getAddress().getCity().equalsIgnoreCase(city)) &&
                                 (phone == null || hotel.getContacts() != null && hotel.getContacts().getPhone() != null &&
                                                   hotel.getContacts().getPhone().replaceAll("[^0-9]", "").contains(phone.replaceAll("[^0-9]", ""))) &&
                                 (email == null || hotel.getContacts().getEmail().equalsIgnoreCase(email)) &&
                                 (amenities == null || amenities.isEmpty() || hotel.getAmenitiesList().stream()
                                         .map(Amenities::getName)
                                         .anyMatch(amenities::contains)) &&
                                 (checkIn == null || hotel.getArrivalTime() != null &&
                                                     hotel.getArrivalTime().getCheckIn() != null &&
                                                     hotel.getArrivalTime().getCheckIn().equalsIgnoreCase(checkIn)) &&
                                 (checkOut == null || hotel.getArrivalTime() != null &&
                                                      hotel.getArrivalTime().getCheckOut() != null &&
                                                      hotel.getArrivalTime().getCheckOut().equalsIgnoreCase(checkOut)))
                .map(convertorToOverviewDto::convert)
                .collect(Collectors.toList());
    }


    public HotelOverviewDto create(HotelCreationDto hotelCreationDto) {
        Hotel hotel = convertorToHotel.convert(hotelCreationDto);
        hotelRepository.save(hotel);
        return convertorToOverviewDto.convert(hotel);
    }

    public void addAmenities(Long hotelId, Set<String> newAmenityNames) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id " + hotelId));

        Set<Amenities> existingAmenities = hotel.getAmenitiesList();

        Set<Amenities> amenitiesToAdd = newAmenityNames.stream()
                .map(name -> existingAmenities.stream()
                        .filter(a -> a.getName().equals(name))
                        .findFirst()
                        .orElseGet(() -> amenitiesRepository.findByName(name)
                                .orElseGet(() -> amenitiesRepository.save(new Amenities(name)))))
                .collect(Collectors.toSet());

        hotel.getAmenitiesList().addAll(amenitiesToAdd);
        hotelRepository.save(hotel);
    }



    public HistogramDto getHistogramData(String param) {
        List<Object[]> results;
        HistogramDto dto = new HistogramDto();

        switch (param.toLowerCase()) {
            case "brand":
                results = hotelRepository.countHotelsByBrand();
                break;
            case "city":
                results = hotelRepository.countHotelsByCity();
                break;
            case "county":
                results = hotelRepository.countHotelsByCounty();
                break;
            case "amenities":
                results = hotelRepository.countHotelsByAmenities();
                break;
            default:
                throw new IllegalArgumentException("Invalid parameter: " + param);
        }

        for (Object[] result : results) {
            String key = (String) result[0];
            Long count = (Long) result[1];
            dto.addEntry(key, count);
        }

        return dto;
    }
}






