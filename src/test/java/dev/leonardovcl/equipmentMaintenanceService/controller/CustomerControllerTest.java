package dev.leonardovcl.equipmentMaintenanceService.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.CustomerRepository;

@WebMvcTest(CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@Test
	public void givenCustomers_shouldReturnOk_WhenGetCustomers() throws Exception {
	
		List<Customer> customerList = Arrays.asList(
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Customer(2L, "Teste02", "teste02@gmail.com", "+5541999999902", "R. 02"),
				new Customer(3L, "Teste03", "teste03@gmail.com", "+5541999999903", "R. 03"));
		
		PagedListHolder<Customer> customerPageListHolder = new PagedListHolder<>(customerList);
		customerPageListHolder.setPageSize(5);
		customerPageListHolder.setPage(0);
		
		Page<Customer> customerPage = new PageImpl<>(customerPageListHolder.getPageList(), PageRequest.of(0, 5), customerList.size());
		
		Mockito.when(customerRepository.findAll(PageRequest.of(0, 5))).thenReturn(customerPage);
		
		mockMvc.perform(
					get("/customers")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
					.andExpect(jsonPath("$[2].name", is("Teste03"))
				);
	}
	
	@Test
	public void givenNoCustomers_shouldReturnNotFound_WhenGetCustomers() throws Exception {
	
		Page<Customer> customerPage = Page.empty();
		
		Mockito.when(customerRepository.findAll(PageRequest.of(0, 5))).thenReturn(customerPage);
		
		mockMvc.perform(
					get("/customers")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound()
				);
	}
	
	@Test
	public void givenExistingCustomer_shouldReturnOk_whenGetCustomerById() throws Exception {
		
		Customer customer = new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		
		mockMvc.perform(
				get("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Teste01"))
			);
	}
	
	@Test
	public void givenNonExistingCustomer_shouldReturnNotFound_whenGetCustomerById() throws Exception {
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()
			);
	}
	
	@Test
	public void givenCustomersWithNameLike_shouldReturnOk_WhenGetCustomersByNameLike() throws Exception {
	
		List<Customer> customerList = Arrays.asList(
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Customer(2L, "Teste02", "teste02@gmail.com", "+5541999999902", "R. 02"),
				new Customer(3L, "Teste03", "teste03@gmail.com", "+5541999999903", "R. 03"));
		
		PagedListHolder<Customer> customerPageListHolder = new PagedListHolder<>(customerList);
		customerPageListHolder.setPageSize(5);
		customerPageListHolder.setPage(0);
		
		Page<Customer> customerPage = new PageImpl<>(customerPageListHolder.getPageList(), PageRequest.of(0, 5), customerList.size());
		
		Mockito.when(customerRepository.findByNameContainingIgnoreCase("teste",PageRequest.of(0, 5))).thenReturn(customerPage);
		
		mockMvc.perform(
					get("/customers/byName/{likePattern}", "teste")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
					.andExpect(jsonPath("$[2].name", is("Teste03"))
				);
	}
	
	@Test
	public void givenNoCustomersWithNameLike_shouldReturnNotFound_WhenGetCustomersByNameLike() throws Exception {
	
		Page<Customer> customerPage = Page.empty();
		
		Mockito.when(customerRepository.findByNameContainingIgnoreCase("teste",PageRequest.of(0, 5))).thenReturn(customerPage);
		
		mockMvc.perform(
				get("/customers/byName/{likePattern}", "teste")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound()
				);
	}
	
	@Test
	public void givenValidCustomer_shouldReturnCreated_WhenCreateCostumer() throws Exception {
		
		Customer newCustomer = new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
		
		Mockito.when(customerRepository.save(newCustomer)).thenReturn(newCustomer);
		
		mockMvc.perform(
				post("/customers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newCustomer)))
				.andExpect(status().isCreated()
			);
	}
	
	@Test
	public void givenInvalidCustomer_shouldReturnBadRequest_WhenCreateCostumer() throws Exception {
		
		Customer newInvalidCustomer = new Customer();
		
		mockMvc.perform(
				post("/customers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newInvalidCustomer)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenValidExistingCustomer_shouldReturnOk_WhenUpdateCostumer() throws Exception {
		
		Customer customer = new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
		Customer updatedCustomer = new Customer(1L, "Teste01Update", "teste01@gmail.com", "+5541999999901", "R. 01");
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Mockito.when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
		
		mockMvc.perform(
				put("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedCustomer)))
				.andExpect(status().isOk()
			);
	}
	
	@Test
	public void givenNonExistingCustomer_shouldReturnBadRequest_WhenUpdateCostumer() throws Exception {
		
		Customer updatedCustomer = new Customer(1L, "Teste01Updated", "teste01@gmail.com", "+5541999999901", "R. 01");
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				put("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedCustomer)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenInvalidExistingCustomer_shouldReturnBadRequest_WhenUpdateCostumer() throws Exception {
		
		Customer customer = new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
		Customer updatedCustomer = new Customer();
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		
		mockMvc.perform(
				put("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedCustomer)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenExistingCustomer_shouldReturnOk_WhenDeleteCostumer() throws Exception {
		
		Customer customer = new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		
		mockMvc.perform(
				delete("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()
			);
	}
	
	@Test
	public void givenNonExistingCustomer_shouldReturnBadRequest_WhenDeleteCostumer() throws Exception {
		
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				delete("/customers/{customerId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()
			);
	}
}
