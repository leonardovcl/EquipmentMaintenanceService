package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
