package dev.leonardovcl.equipmentMaintenanceService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.leonardovcl.equipmentMaintenanceService.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}
