package services;

import java.io.IOException;
import java.util.List;

import crud.service.CrudService1;
import models.Policy;

public class PolicyService {
    private final CrudService1 crudService = new CrudService1();

    // Add new policy
    public void addPolicy(Policy policy) throws IOException {
        crudService.add(policy);
    }

    // Get all policies
    public List<Object> getAllPolicies() throws IOException {
        return crudService.getAll(new Policy());
    }

    // Get policy by ID
    public Policy getPolicyById(String id) throws IOException {
        return (Policy) crudService.getById(id, new Policy());
    }

    // Update policy
    public void updatePolicy(String id, Policy updatedPolicy) throws IOException {
        crudService.update(id, updatedPolicy, new Policy());
    }

    // Delete policy
    public void deletePolicy(String id) throws IOException {
        crudService.delete(id, new Policy());
    }
}
