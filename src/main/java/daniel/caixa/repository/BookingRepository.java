package daniel.caixa.repository;

import daniel.caixa.entity.Booking;
import daniel.caixa.entity.BookingStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    public Booking findActiveByVehicleId(Long vehicleId) {
        return find("vehicleId = ?1 and status = ?2", vehicleId, BookingStatus.RENTED)
                .firstResult();
    }

    public boolean hasDateConflict(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        return count("vehicleId = ?1 and status = ?2 and startDate <= ?3 and endDate >= ?4",
                vehicleId, BookingStatus.RENTED, endDate, startDate) > 0;
    }
}

