package services;

import java.io.IOException;
import java.util.List;

import crud.service.CrudService1;
import models.Claim;

public class ClaimService {
    private final CrudService1 crudService = new CrudService1();

    // Add new claim
    public void addClaim(Claim claim) throws IOException {
        crudService.add(claim);
    }

    // Get all claims
    public List<Object> getAllClaims() throws IOException {
        return (List<Object>) crudService.getAll(new Claim());
    }

    // Get claim by ID
    public Claim getClaimById(String id) throws IOException {
        return (Claim) crudService.getById(id, new Claim());
    }

    // Update claim
    public void updateClaim(String id, Claim updatedClaim) throws IOException {
        crudService.update(id, updatedClaim, new Claim());
    }

    // Delete claim
    public void deleteClaim(String id) throws IOException {
        crudService.delete(id, new Claim());
    }
}
