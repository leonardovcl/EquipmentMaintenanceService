package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee;

public interface MaintenanceEmployeeRepository extends JpaRepository<MaintenanceEmployee, Long> {

}
