package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
