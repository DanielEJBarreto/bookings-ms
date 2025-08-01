//package daniel.caixa.BookingTests;
//
//import daniel.caixa.DTO.BookingRequest;
//import daniel.caixa.Mapper.BookingMapper;
//import daniel.caixa.Repository.BookingRepository;
//import daniel.caixa.Service.BookingService;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//@QuarkusTest
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class BookingRequestIntegrationTests {
//
//    @Inject
//    BookingService bookingService;
//
//    @Inject
//    VehicleRepository vehicleRepository;
//
//    @Inject
//    BookingRepository bookingRepository;
//
//    @Inject
//    VehicleMapper Vmapper;
//
//    @Inject
//    BookingMapper Bmapper;
//
//    //Criar reserva com dados válidos
//    @Transactional
//    @Test
//    void CreateBookingWithValidData() {
//        BookingRequest bookingRequest = new BookingRequest(1L, "Daniel",
//                LocalDate.now().plusDays(1), LocalDate.now().plusDays(7));
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(bookingRequest)
//                .post("/bookings")
//                .then()
//                .statusCode(201);
//    }
//
//    //Criar reserva com data inválida
//    @Transactional
//    @Test
//    void CreateBookingWithInvalidDate(){
//        BookingRequest bookingRequest = new BookingRequest(1L, "Daniel",
//                LocalDate.now().minusDays(1), LocalDate.now().plusDays(7));
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(bookingRequest)
//                .post("/bookings")
//                .then()
//                .statusCode(400);
//    }
//
//    //Cancelar reserva
//    @Transactional
//    @Test
//    void CancelBooking() {
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParam("id", 1L)
//                .body("""
//                        {"status": "CANCELED"}
//                        """)
//                .patch("/bookings/{id}/alter")
//                .then()
//                .statusCode(200);
//    }
//
//    //Tentar cancelar reserva já cancelada
//    @Transactional
//    @Test
//    void CancelBookingAlreadyCanceled() {
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParam("id", 2L)
//                .body("""
//                        {"status": "CANCELED"}
//                        """)
//                .patch("/bookings/{id}/alter")
//                .then()
//                .statusCode(409);
//    }
//
//    //Finalizar reserva
//    @Transactional
//    @Test
//    void FinishBooking() {
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParam("id", 3L)
//                .body("""
//                        {"status": "FINISHED"}
//                        """)
//                .patch("/bookings/{id}/alter")
//                .then()
//                .statusCode(200);
//    }
//}
