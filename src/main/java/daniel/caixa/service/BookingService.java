package daniel.caixa.service;

import daniel.caixa.client.VehicleAPIClient;
import daniel.caixa.dto.BookingRequest;
import daniel.caixa.dto.BookingResponse;
import daniel.caixa.entity.Booking;
import daniel.caixa.entity.BookingStatus;
import daniel.caixa.exception.*;
import daniel.caixa.mapper.BookingMapper;
import daniel.caixa.repository.BookingRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookingService {

    @Inject
    BookingRepository repository;

    @Inject
    BookingMapper mapper;

//    @Inject
    @RestClient
    VehicleAPIClient vehicleAPIClient;

    public List<BookingResponse> listAll() {
        return repository.listAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse findById(Long id) {
        return repository.findByIdOptional(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional
    public BookingResponse create(BookingRequest dto) {

        VehicleAPIClient.Vehicle vehicle = vehicleAPIClient.findVehicleById(dto.getVehicleId());

        // Regra 1: verificar se o veículo existe e está disponível
        if (vehicle == null) {
            throw new VehicleNotFoundException("Veículo não encontrado");
        }

        if (!vehicle.status().equals("AVAILABLE")) {
            throw new VehicleUnavailableException("Veículo indisponível para reserva");
        }

        // Regra 2: startDate deve ser hoje ou futuro
        if (dto.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("A data de início da reserva deve ser hoje ou no futuro");
        }

        // Regra 3: endDate ≥ startDate
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidReservationDateException("A data de término da reserva deve ser igual ou posterior à data de início");
        }

        //Alterando Status do Veiculo para RENTED
        vehicleAPIClient.updateStatus(dto.getVehicleId(), new VehicleAPIClient.Vehicle("RENTED"));

        // Mapeamento e persistência
        Booking entity = mapper.toEntity(dto);
        repository.persist(entity);
        return mapper.toResponse(entity);

    }

    @Transactional
    public void alter(Long id, BookingStatus newStatus) {
        Booking booking = repository.findByIdOptional(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        BookingStatus currentStatus = booking.getStatus();

        // Regra 1: Só cancelar se Criada
        if (booking.getStatus() != BookingStatus.CREATED) {
            throw new InvalidReservationStatusException("Booking already " + booking.getStatus());
        }

        //Alterando Status do Veiculo para AVAILABLE
        VehicleAPIClient.Vehicle vehicle = vehicleAPIClient.findVehicleById(id);
        vehicleAPIClient.updateStatus(id, new VehicleAPIClient.Vehicle("AVAILABLE"));

        //Alterando Status do Booking para novo Status
        booking.setStatus(newStatus);
    }
}

