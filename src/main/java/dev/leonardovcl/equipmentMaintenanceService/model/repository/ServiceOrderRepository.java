package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
	
	public Page<ServiceOrder> findByCustomerId(Long customerId, Pageable pageable);
}
