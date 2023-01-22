package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
