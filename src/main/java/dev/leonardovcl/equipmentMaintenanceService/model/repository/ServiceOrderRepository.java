package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

}
