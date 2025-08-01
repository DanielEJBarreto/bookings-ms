package daniel.caixa.client;

import io.quarkus.logging.Log;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "vehicles-ms")
@Path("/vehicles")
public interface VehicleAPIClient {

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        String s = response.readEntity(String.class);
        Log.error("Deu erro aqui! "+s);
        return new RuntimeException(s);
    }

    @GET
    @Path("/{vehicleId}")
    Vehicle findVehicleById(@PathParam("vehicleId") Long id);

    @PATCH
    @Path("/{vehicleId}/status")
    String updateStatus(@PathParam("vehicleId") Long id, Vehicle status);

    record Vehicle(String status) {}
}
