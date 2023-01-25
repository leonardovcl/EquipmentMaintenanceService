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
import dev.leonardovcl.equipmentMaintenanceService.model.Customer;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Customer>> getCustomers(
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Customer> customersList = customerRepository.findAll(pageable);
		
		if(customersList.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(customersList.getContent(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{customerId}", produces = "application/json")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Long customerId) {
		
		Optional<Customer> customer = customerRepository.findById(customerId);
		
		if(customer.isPresent()) {
			return new ResponseEntity<>(customer.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/byName/{likePattern}", produces = "application/json")
	public ResponseEntity<List<Customer>> getCustomersByNameLike(
											@PathVariable("likePattern") String likePattern,
											@RequestParam(value = "page", defaultValue = "0") int page,
											@RequestParam(value = "size", defaultValue = "5") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Customer> customersPage = customerRepository.findByNameContainingIgnoreCase(likePattern, pageable);
		
		if(customersPage.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(customersPage.getContent(), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer newCustomer,
													BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(customerRepository.save(newCustomer), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{customerId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long customerId,
													@RequestBody @Valid Customer updatedCustomer,
													BindingResult bindingResult) {
		
		Optional<Customer> customer = customerRepository.findById(customerId);
		
		if(!customer.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(customerRepository.save(updatedCustomer), HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Long customerId) {
		
		Optional<Customer> customer = customerRepository.findById(customerId);
		
		if(!customer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
