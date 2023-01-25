package dev.leonardovcl.equipmentMaintenanceService.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.Status;
import dev.leonardovcl.equipmentMaintenanceService.model.Status.Stage;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;
import dev.leonardovcl.equipmentMaintenanceService.service.ServiceOrderService;

@RestController
@RequestMapping("/serviceOrders")
@CrossOrigin
public class ServiceOrderController {

	@Autowired
	ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	ServiceOrderService serviceOrderService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ServiceOrder>> getServiceOrders(
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<ServiceOrder> serviceOrdersPage = serviceOrderRepository.findAll(pageable);
		
		if(serviceOrdersPage.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(serviceOrdersPage.getContent(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{serviceOrderId}", produces = "application/json")
	public ResponseEntity<ServiceOrder> getServiceOrderById(@PathVariable("serviceOrderId") Long serviceOrderId) {
		
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(serviceOrder.isPresent()) {
			return new ResponseEntity<>(serviceOrder.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/customer/{customerId}", produces = "application/json")
	public ResponseEntity<List<ServiceOrder>> getServiceOrdersByCustomerId(
											@PathVariable("customerId") Long customerId,
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<ServiceOrder> serviceOrdersPage = serviceOrderRepository.findByCustomerId(customerId, pageable);
		
		if(serviceOrdersPage.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(serviceOrdersPage.getContent(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/pending", produces = "application/json")
	public ResponseEntity<List<ServiceOrder>> getPendingServiceOrders(
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		List<ServiceOrder> serviceOrdersList = serviceOrderService.findByPendingStatus();
		
		if(serviceOrdersList.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		
		PagedListHolder<ServiceOrder> serviceOrderPageListHolder = new PagedListHolder<>(serviceOrdersList);
		serviceOrderPageListHolder.setPageSize(size);
		serviceOrderPageListHolder.setPage(page);
		
		Page<ServiceOrder> serviceOrdersPage = new PageImpl<>(serviceOrderPageListHolder.getPageList(), pageable, serviceOrdersList.size());
		
		return new ResponseEntity<>(serviceOrdersPage.getContent(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/stage", produces = "application/json")
	public ResponseEntity<List<ServiceOrder>> getServiceOrdersByLastStage(
											@RequestParam(value = "stageName") String stageName,
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		List<ServiceOrder> serviceOrdersList = serviceOrderService.findByLastStatus(Stage.valueOf(stageName.toUpperCase()));
		
		if(serviceOrdersList.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		
		PagedListHolder<ServiceOrder> serviceOrderPageListHolder = new PagedListHolder<>(serviceOrdersList);
		serviceOrderPageListHolder.setPageSize(size);
		serviceOrderPageListHolder.setPage(page);
		
		Page<ServiceOrder> serviceOrdersPage = new PageImpl<>(serviceOrderPageListHolder.getPageList(), pageable, serviceOrdersList.size());
		
		return new ResponseEntity<>(serviceOrdersPage.getContent(), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<ServiceOrder> createServiceOrder(@RequestBody @Valid ServiceOrder newServiceOrder,
													BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(serviceOrderRepository.save(newServiceOrder), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{serviceOrderId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ServiceOrder> updateServiceOrder(@PathVariable("serviceOrderId") Long serviceOrderId,
													@RequestBody @Valid ServiceOrder updatedServiceOrder,
													BindingResult bindingResult) {
		
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(!serviceOrder.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(serviceOrderRepository.save(updatedServiceOrder), HttpStatus.OK);
	}
	
	@PatchMapping(path = "/{serviceOrderId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ServiceOrder> updateServiceOrderStatus(@PathVariable("serviceOrderId") Long serviceOrderId,
															@RequestBody @Valid Status status,
															BindingResult bindingResult) {
		
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(!serviceOrder.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		List<Status> newStatusLog = serviceOrder.get().getStatusLog();
		newStatusLog.add(status);
		
		ServiceOrder updatedServiceOrder = serviceOrder.get();
		updatedServiceOrder.setStatusLog(newStatusLog);
		
		return new ResponseEntity<>(serviceOrderRepository.save(updatedServiceOrder), HttpStatus.OK);
	}
	
	@DeleteMapping("/{serviceOrderId}")
	public ResponseEntity<ServiceOrder> deleteServiceOrder(@PathVariable("serviceOrderId") Long serviceOrderId) {
		
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(!serviceOrder.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
