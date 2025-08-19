package daniel.caixa.resource;

import daniel.caixa.dto.AlterBookingStatusRequest;
import daniel.caixa.dto.BookingRequest;
import daniel.caixa.dto.BookingResponse;
import daniel.caixa.entity.Booking;
import daniel.caixa.service.BookingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookingResource {

    BookingService bookingService;

    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GET
    public List<BookingResponse> listAll() {
        return bookingService.listAll();
    }

    @GET
    @Path("/{id}")
    public BookingResponse findById(@PathParam("id") Long id) {
        return bookingService.findById(id);
    }

    @GET
    @Path("/vehicle/{vehicleId}")
    public Response getBookingByVehicleId(@PathParam("vehicleId") Long vehicleId) {
        BookingResponse booking = bookingService.getActiveBookingByVehicleId(vehicleId);

        if (booking == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No active booking found for vehicle " + vehicleId)
                    .build();
        }

        return Response.ok(booking).build();
    }


    @POST
    public Response create(@Valid BookingRequest dto) {
        BookingResponse created = bookingService.create(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Path("/{id}/alter")
    public Response alter(@PathParam("id") Long id, AlterBookingStatusRequest dto) {
        bookingService.alter(id, dto.getStatus());
        return Response.ok().build();
    }

}

