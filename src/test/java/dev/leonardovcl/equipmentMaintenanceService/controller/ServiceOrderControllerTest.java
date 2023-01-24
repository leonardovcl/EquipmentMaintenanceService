package dev.leonardovcl.equipmentMaintenanceService.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;
import dev.leonardovcl.equipmentMaintenanceService.model.Equipment;
import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee;
import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.Status;
import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee.Position;
import dev.leonardovcl.equipmentMaintenanceService.model.Status.Stage;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;
import dev.leonardovcl.equipmentMaintenanceService.service.ServiceOrderService;

@WebMvcTest(ServiceOrderController.class)
public class ServiceOrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	private ServiceOrderRepository serviceOrderRepository;
	
	@MockBean
	private ServiceOrderService serviceOrderService;
	
	@Test
	public void givenServiceOrders_shouldReturnOk_WhenGetServiceOrders() throws Exception {
	
		List<ServiceOrder> serviceOrderList = Arrays.asList(
				new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01"),
				new ServiceOrder(2L, new Customer(2L, "Teste02", "teste02@gmail.com", "+5541999999902", "R. 02"), new Equipment(2L, "Type02", "Brand02"), "Problem Description02"),
				new ServiceOrder(3L, new Customer(2L, "Teste02", "teste02@gmail.com", "+5541999999902", "R. 02"), new Equipment(3L, "Type03", "Brand03"), "Problem Description03"));
		
		PagedListHolder<ServiceOrder> serviceOrderPageListHolder = new PagedListHolder<>(serviceOrderList);
		serviceOrderPageListHolder.setPageSize(5);
		serviceOrderPageListHolder.setPage(0);
		
		Page<ServiceOrder> serviceOrderPage = new PageImpl<>(serviceOrderPageListHolder.getPageList(), PageRequest.of(0, 5), serviceOrderList.size());
		
		Mockito.when(serviceOrderRepository.findAll(PageRequest.of(0, 5))).thenReturn(serviceOrderPage);
		
		mockMvc.perform(
					get("/serviceOrders")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
					.andExpect(jsonPath("$[1].id", is(2))
				);
	}
	
	@Test
	public void givenNoServiceOrders_shouldReturnNotFound_WhenGetServiceOrders() throws Exception {
	
		Page<ServiceOrder> serviceOrdersPage = Page.empty();
		
		Mockito.when(serviceOrderRepository.findAll(PageRequest.of(0, 5))).thenReturn(serviceOrdersPage);
		
		mockMvc.perform(
					get("/serviceOrders")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound()
				);
	}
	
	@Test
	public void givenExistingServiceOrder_shouldReturnOk_whenGetServiceOrderById() throws Exception {
		
		ServiceOrder serviceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01");
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.of(serviceOrder));
		
		mockMvc.perform(
				get("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.customer.name", is("Teste01"))
			);
	}
	
	@Test
	public void givenNonExistingServiceOrder_shouldReturnNotFound_whenGetServiceOrderById() throws Exception {
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()
			);
	}
	
	@Test
	public void givenPendingServiceOrders_shouldReturnOk_WhenGetSPendingServiceOrders() throws Exception {
	
		ServiceOrder serviceOrder01 = new ServiceOrder(
				1L,
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Equipment(1L, "Type01", "Brand01"),
				"Problem Description01"
		);
		
		ServiceOrder serviceOrder02 = new ServiceOrder(
				2L,
				new Customer(1L, "Teste012", "teste02@gmail.com", "+5541999999902", "R. 02"),
				new Equipment(1L, "Type02", "Brand02"),
				"Problem Description02"
		);
		
		List<Status> statusLog01 = Arrays.asList(
					new Status(1L, serviceOrder01, new MaintenanceEmployee(), Stage.RECEIVED, "Description01"),
					new Status(2L, serviceOrder01, new MaintenanceEmployee(), Stage.INITIADED, "Description02"),
					new Status(3L, serviceOrder01, new MaintenanceEmployee(), Stage.ONHOLD, "Description03"),
					new Status(4L, serviceOrder01, new MaintenanceEmployee(), Stage.RESUMED, "Description04")
				);
		
		serviceOrder01.setStatusLog(statusLog01);
		
		List<Status> statusLog02 = Arrays.asList(
				new Status(1L, serviceOrder02, new MaintenanceEmployee(), Stage.RECEIVED, "Description01"),
				new Status(2L, serviceOrder02, new MaintenanceEmployee(), Stage.INITIADED, "Description02")
			);
	
		serviceOrder02.setStatusLog(statusLog02);
		
		List<ServiceOrder> serviceOrderList = Arrays.asList(serviceOrder01, serviceOrder02);
		
		Mockito.when(serviceOrderService.findByPendingStatus()).thenReturn(serviceOrderList);
		
		mockMvc.perform(
					get("/serviceOrders/pending")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(2)))
					.andExpect(jsonPath("$[1].id", is(2))
				);
	}
	
	@Test
	public void givenNoPendingerviceOrders_shouldReturnNotFound_WhenGetPendingServiceOrders() throws Exception {
	
		Mockito.when(serviceOrderService.findByPendingStatus()).thenReturn(new ArrayList<ServiceOrder>());
		
		mockMvc.perform(
					get("/serviceOrders/pending")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound()
				);
	}
	
	@Test
	public void givenValidServiceOrder_shouldReturnCreated_WhenCreateServiceOrder() throws Exception {
		
		ServiceOrder newServiceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01");
		
		Mockito.when(serviceOrderRepository.save(newServiceOrder)).thenReturn(newServiceOrder);
		
		mockMvc.perform(
				post("/serviceOrders")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newServiceOrder)))
				.andExpect(status().isCreated()
			);
	}
	
	@Test
	public void givenInvalidServiceOrder_shouldReturnBadRequest_WhenCreateServiceOrder() throws Exception {
		
		ServiceOrder newInvalidServiceOrder = new ServiceOrder();
		
		mockMvc.perform(
				post("/serviceOrders")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newInvalidServiceOrder)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenValidExistingServiceOrder_shouldReturnOk_WhenUpdateServiceOrder() throws Exception {
		
		ServiceOrder serviceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01");
		ServiceOrder updatedServiceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01Updated");
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.of(serviceOrder));
		Mockito.when(serviceOrderRepository.save(updatedServiceOrder)).thenReturn(updatedServiceOrder);
		
		mockMvc.perform(
				put("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedServiceOrder)))
				.andExpect(status().isOk()
			);
	}
	
	@Test
	public void givenNonExistingServiceOrder_shouldReturnBadRequest_WhenUpdateServiceOrder() throws Exception {
		
		ServiceOrder updatedServiceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01Updated");
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				put("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedServiceOrder)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenInvalidExistingServiceOrder_shouldReturnBadRequest_WhenUpdateServiceOrder() throws Exception {
		
		ServiceOrder serviceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01");
		ServiceOrder updatedServiceOrder = new ServiceOrder();
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.of(serviceOrder));
		
		mockMvc.perform(
				put("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedServiceOrder)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenExistingServiceOrderandValidStatus_shouldReturnOk_WhenUpdateServiceOrderStatus() throws Exception {
		
		ServiceOrder serviceOrder = new ServiceOrder(
				1L,
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Equipment(1L, "Type01", "Brand01"),
				"Problem Description01"
		);
		
		List<Status> statusLog = new ArrayList<>(Arrays.asList(
					new Status(1L, serviceOrder, new MaintenanceEmployee(2L, "Teste02", Position.ASSISTANT), Stage.RECEIVED, "Description01"),
					new Status(2L, serviceOrder, new MaintenanceEmployee(2L, "Teste02", Position.ASSISTANT), Stage.INITIADED, "Description02")
				));
		
		serviceOrder.setStatusLog(statusLog);
		
		ServiceOrder updatedServiceOrder = new ServiceOrder(
				1L,
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Equipment(1L, "Type01", "Brand01"),
				"Problem Description01"
		);
		
		Status newStatus = new Status(3L, serviceOrder, new MaintenanceEmployee(1L, "Teste01", Position.LEADER), Stage.FINISHED, "Description03");
		
		statusLog.add(newStatus);
		
		updatedServiceOrder.setStatusLog(statusLog);
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.of(serviceOrder));
		Mockito.when(serviceOrderRepository.save(updatedServiceOrder)).thenReturn(updatedServiceOrder);
		
		mockMvc.perform(
				patch("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newStatus)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.statusLog[2].stage", is(Stage.FINISHED.name()))
				);
	}
	
	@Test
	public void givenNonExistingServiceOrder_shouldReturnBadRequest_WhenUpdateServiceOrderStatus() throws Exception {
		
		Status newStatus = new Status(3L, new ServiceOrder(), new MaintenanceEmployee(1L, "Teste01", Position.LEADER), Stage.FINISHED, "Description03");
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				patch("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newStatus)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenInvalidStatus_shouldReturnBadRequest_WhenUppdateServiceOrderStatus() throws Exception {
		
		ServiceOrder serviceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01");
		Status newStatus = new Status();
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.of(serviceOrder));
		
		mockMvc.perform(
				patch("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newStatus)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenExistingServiceOrder_shouldReturnOk_WhenDeleteServiceOrder() throws Exception {
		
		ServiceOrder serviceOrder = new ServiceOrder(1L, new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"), new Equipment(1L, "Type01", "Brand01"), "Problem Description01");
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.of(serviceOrder));
		
		mockMvc.perform(
				delete("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()
			);
	}
	
	@Test
	public void givenNonExistingServiceOrder_shouldReturnBadRequest_WhenDeleteServiceOrder() throws Exception {
		
		Mockito.when(serviceOrderRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				delete("/serviceOrders/{serviceOrderId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()
			);
	}
}
