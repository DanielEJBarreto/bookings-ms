package daniel.caixa.mapper;

import daniel.caixa.dto.BookingRequest;
import daniel.caixa.dto.BookingResponse;
import daniel.caixa.entity.Booking;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookingMapper {

    public Booking toEntity(BookingRequest dto) {
        Booking b = new Booking();
        b.setVehicleId(dto.getVehicleId());
        b.setCustomerId(dto.getCustomerName());
        b.setStartDate(dto.getStartDate());
        b.setEndDate(dto.getEndDate());
        b.setStatus(dto.getStatus());
        return b;
    }

    public BookingResponse toResponse(Booking b) {
        BookingResponse dto = new BookingResponse();
        dto.setId(b.getId());
        dto.setVehicleId(b.getVehicleId());
        dto.setCustomerName(b.getCustomerId());
        dto.setStartDate(b.getStartDate());
        dto.setEndDate(b.getEndDate());
        dto.setStatus(b.getStatus());
        return dto;
    }
}

