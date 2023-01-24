package dev.leonardovcl.equipmentMaintenanceService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;
import dev.leonardovcl.equipmentMaintenanceService.model.Equipment;
import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee;
import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.Status;
import dev.leonardovcl.equipmentMaintenanceService.model.Status.Stage;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;

@WebMvcTest(ServiceOrderService.class)
public class ServiceOrderServiceTest {

	@Autowired
	ServiceOrderService serviceOrderService;
	
	@MockBean
	private ServiceOrderRepository serviceOrderRepository;
	
	@Test
	public void givenPendingStatusLog_shouldReturnTrue_WhenisPending() {
		
		ServiceOrder serviceOrder = new ServiceOrder(
				1L,
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Equipment(1L, "Type01", "Brand01"),
				"Problem Description01"
		);
		
		List<Status> statusLog = Arrays.asList(
					new Status(1L, serviceOrder, new MaintenanceEmployee(), Stage.RECEIVED, "Description01"),
					new Status(2L, serviceOrder, new MaintenanceEmployee(), Stage.INITIADED, "Description02"),
					new Status(3L, serviceOrder, new MaintenanceEmployee(), Stage.ONHOLD, "Description03"),
					new Status(4L, serviceOrder, new MaintenanceEmployee(), Stage.RESUMED, "Description04")
				);
		
		serviceOrder.setStatusLog(statusLog);
		
		Boolean response = serviceOrderService.isPending(statusLog);
		
		assertEquals(true, response);
	}
	
	@Test
	public void givenFinishedStatusLog_shouldReturnFalse_WhenisPending() {
		
		ServiceOrder serviceOrder = new ServiceOrder(
				1L,
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Equipment(1L, "Type01", "Brand01"),
				"Problem Description01"
		);
		
		List<Status> statusLog = Arrays.asList(
					new Status(1L, serviceOrder, new MaintenanceEmployee(), Stage.RECEIVED, "Description01"),
					new Status(2L, serviceOrder, new MaintenanceEmployee(), Stage.INITIADED, "Description02"),
					new Status(3L, serviceOrder, new MaintenanceEmployee(), Stage.ONHOLD, "Description03"),
					new Status(4L, serviceOrder, new MaintenanceEmployee(), Stage.RESUMED, "Description04"),
					new Status(5L, serviceOrder, new MaintenanceEmployee(), Stage.FINISHED, "Description05")
				);
		
		serviceOrder.setStatusLog(statusLog);
		
		Boolean response = serviceOrderService.isPending(statusLog);
		
		assertEquals(false, response);
	}
	
	@Test
	public void givenTwoPendingServiceOrder_shouldReturnTwoServiceOrder_WhenfindByPendingStatus() {
		
		ServiceOrder serviceOrder01 = new ServiceOrder(
				1L,
				new Customer(1L, "Teste01", "teste01@gmail.com", "+5541999999901", "R. 01"),
				new Equipment(1L, "Type01", "Brand01"),
				"Problem Description01"
		);
		
		ServiceOrder serviceOrder02 = new ServiceOrder(
				2L,
				new Customer(2L, "Teste02", "teste02@gmail.com", "+5541999999902", "R. 02"),
				new Equipment(2L, "Type02", "Brand02"),
				"Problem Description02"
		);
		
		ServiceOrder serviceOrder03 = new ServiceOrder(
				3L,
				new Customer(3L, "Teste03", "teste03@gmail.com", "+5541999999903", "R. 03"),
				new Equipment(3L, "Type03", "Brand03"),
				"Problem Description03"
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
				new Status(2L, serviceOrder02, new MaintenanceEmployee(), Stage.INITIADED, "Description02"),
				new Status(3L, serviceOrder02, new MaintenanceEmployee(), Stage.FINISHED, "Description03")
			);
	
		serviceOrder02.setStatusLog(statusLog02);
		
		List<Status> statusLog03 = Arrays.asList(
				new Status(1L, serviceOrder01, new MaintenanceEmployee(), Stage.RECEIVED, "Description01"),
				new Status(2L, serviceOrder01, new MaintenanceEmployee(), Stage.INITIADED, "Description02")
			);
	
		serviceOrder03.setStatusLog(statusLog03);
		
		List<ServiceOrder> serviceOrderList = Arrays.asList(serviceOrder01, serviceOrder02, serviceOrder03);
		
		Mockito.when(serviceOrderRepository.findAll()).thenReturn(serviceOrderList);
		
		List<ServiceOrder> response = serviceOrderService.findByPendingStatus();
		
		assertEquals(2, response.size());
	}
}
