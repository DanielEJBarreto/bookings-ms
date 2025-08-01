package daniel.caixa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import daniel.caixa.entity.BookingStatus;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

//@RegisterForReflection
public class BookingRequest {

    private Long vehicleId;

    @NotBlank
    private String customerName;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @Future
    private LocalDate endDate;


    private BookingStatus status = BookingStatus.CREATED;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}

