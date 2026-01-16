package br.com.technosou.controller;

import br.com.technosou.dto.OpportunityDTO;
import br.com.technosou.service.OpportunityService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/api/opportunity")
@Authenticated
public class OpportunityController {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    OpportunityService opportunityService;

    @GET
    @Path("/report")
    @RolesAllowed({"manager", "user"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<OpportunityDTO> generateReport() {

        return opportunityService.generateOpportunityData();
    }

}
