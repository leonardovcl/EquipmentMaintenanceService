package dev.leonardovcl.equipmentMaintenanceService.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.MaintenanceEmployeeRepository;

@RestController
@RequestMapping("/employees")
@CrossOrigin
public class MaintenanceEmployeeController {

	@Autowired
	MaintenanceEmployeeRepository maintenanceEmployeeRepository;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<MaintenanceEmployee>> getMaintenanceEmployees(
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<MaintenanceEmployee> maintenanceEmployeesList = maintenanceEmployeeRepository.findAll(pageable);
		
		if(maintenanceEmployeesList.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(maintenanceEmployeesList.getContent(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{employeeId}", produces = "application/json")
	public ResponseEntity<MaintenanceEmployee> getMaintenanceEmployeById(@PathVariable("employeeId") Long employeeId) {
		
		Optional<MaintenanceEmployee> maintenanceEmployee = maintenanceEmployeeRepository.findById(employeeId);
		
		if(maintenanceEmployee.isPresent()) {
			return new ResponseEntity<>(maintenanceEmployee.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<MaintenanceEmployee> createMaintenanceEmploye(@RequestBody @Valid MaintenanceEmployee newMaintenanceEmployee,
													BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(maintenanceEmployeeRepository.save(newMaintenanceEmployee), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{employeeId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<MaintenanceEmployee> updateMaintenanceEmploye(@PathVariable("employeeId") Long employeeId,
													@RequestBody @Valid MaintenanceEmployee updatedMaintenanceEmployee,
													BindingResult bindingResult) {
		
		Optional<MaintenanceEmployee> maintenanceEmployee = maintenanceEmployeeRepository.findById(employeeId);
		
		if(!maintenanceEmployee.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(maintenanceEmployeeRepository.save(updatedMaintenanceEmployee), HttpStatus.OK);
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<MaintenanceEmployee> deleteMaintenanceEmploye(@PathVariable("employeeId") Long employeeId) {
		
		Optional<MaintenanceEmployee> maintenanceEmployee = maintenanceEmployeeRepository.findById(employeeId);
		
		if(!maintenanceEmployee.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
