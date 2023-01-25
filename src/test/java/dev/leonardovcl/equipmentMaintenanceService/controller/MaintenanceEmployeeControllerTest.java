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

import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee;
import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee.Position;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.MaintenanceEmployeeRepository;

@WebMvcTest(MaintenanceEmployeeController.class)
@ActiveProfiles("test")
public class MaintenanceEmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	private MaintenanceEmployeeRepository maintenanceEmployeeRepository;
	
	@Test
	public void givenMaintenanceEmployees_shouldReturnOk_WhenGetaintenanceEmployees() throws Exception {
	
		List<MaintenanceEmployee> maintenanceEmployeeList = Arrays.asList(
				new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT),
				new MaintenanceEmployee(2L, "Teste02", Position.LEADER),
				new MaintenanceEmployee(3L, "Teste03", Position.PRINCIPAL));
		
		PagedListHolder<MaintenanceEmployee> maintenanceEmployeePageListHolder = new PagedListHolder<>(maintenanceEmployeeList);
		maintenanceEmployeePageListHolder.setPageSize(5);
		maintenanceEmployeePageListHolder.setPage(0);
		
		Page<MaintenanceEmployee> maintenanceEmployeePage = new PageImpl<>(maintenanceEmployeePageListHolder.getPageList(), PageRequest.of(0, 5), maintenanceEmployeeList.size());
		
		Mockito.when(maintenanceEmployeeRepository.findAll(PageRequest.of(0, 5))).thenReturn(maintenanceEmployeePage);
		
		mockMvc.perform(
					get("/employees")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
					.andExpect(jsonPath("$[2].name", is("Teste03"))
				);
	}
	
	@Test
	public void givenNoMaintenanceEmployees_shouldReturnNotFound_WhenGetMaintenanceEmployees() throws Exception {
	
		Page<MaintenanceEmployee> maintenanceEmployeePage = Page.empty();
		
		Mockito.when(maintenanceEmployeeRepository.findAll(PageRequest.of(0, 5))).thenReturn(maintenanceEmployeePage);
		
		mockMvc.perform(
					get("/employees")
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound()
				);
	}
	
	@Test
	public void givenExistingMaintenanceEmployee_shouldReturnOk_whenGetMaintenanceEmployeeById() throws Exception {
		
		MaintenanceEmployee employee = new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT);
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		
		mockMvc.perform(
				get("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Teste01"))
			);
	}
	
	@Test
	public void givenNonExistingMaintenanceEmployee_shouldReturnNotFound_whenGetCMaintenanceEmployeeById() throws Exception {
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()
			);
	}
	
	@Test
	public void givenValidMaintenanceEmployee_shouldReturnCreated_WhenCreateMaintenanceEmployee() throws Exception {
		
		MaintenanceEmployee newMaintenanceEmployee = new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT);
		
		Mockito.when(maintenanceEmployeeRepository.save(newMaintenanceEmployee)).thenReturn(newMaintenanceEmployee);
		
		mockMvc.perform(
				post("/employees")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newMaintenanceEmployee)))
				.andExpect(status().isCreated()
			);
	}
	
	@Test
	public void givenInvalidMaintenanceEmployee_shouldReturnBadRequest_WhenCreateMaintenanceEmployee() throws Exception {
		
		MaintenanceEmployee newInvalidMaintenanceEmployee = new MaintenanceEmployee();
		
		mockMvc.perform(
				post("/employees")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newInvalidMaintenanceEmployee)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenValidExistingMaintenanceEmployee_shouldReturnOk_WhenUpdateMaintenanceEmployee() throws Exception {
		
		MaintenanceEmployee maintenanceEmployee = new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT);
		MaintenanceEmployee updatedMaintenanceEmployee = new MaintenanceEmployee(1L, "Teste01Updated", Position.ASSISTANT);
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.of(maintenanceEmployee));
		Mockito.when(maintenanceEmployeeRepository.save(updatedMaintenanceEmployee)).thenReturn(updatedMaintenanceEmployee);
		
		mockMvc.perform(
				put("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedMaintenanceEmployee)))
				.andExpect(status().isOk()
			);
	}
	
	@Test
	public void givenNonExistingMaintenanceEmployee_shouldReturnBadRequest_WhenUpdateMaintenanceEmployee() throws Exception {
		
		MaintenanceEmployee updatedMaintenanceEmployee = new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT);
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				put("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedMaintenanceEmployee)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenInvalidExistingMaintenanceEmployee_shouldReturnBadRequest_WhenUpdateMaintenanceEmployee() throws Exception {
		
		MaintenanceEmployee maintenanceEmployee = new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT);
		MaintenanceEmployee updatedMaintenanceEmployee = new MaintenanceEmployee();
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.of(maintenanceEmployee));
		
		mockMvc.perform(
				put("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedMaintenanceEmployee)))
				.andExpect(status().isBadRequest()
			);
	}
	
	@Test
	public void givenExistingMaintenanceEmployee_shouldReturnOk_WhenDeleteMaintenanceEmployee() throws Exception {
		
		MaintenanceEmployee maintenanceEmployee = new MaintenanceEmployee(1L, "Teste01", Position.ASSISTANT);
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.of(maintenanceEmployee));
		
		mockMvc.perform(
				delete("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()
			);
	}
	
	@Test
	public void givenNonExistingCMaintenanceEmployee_shouldReturnBadRequest_WhenDeleteMaintenanceEmployee() throws Exception {
		
		Mockito.when(maintenanceEmployeeRepository.findById(1L)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				delete("/employees/{employeeId}",1L)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()
			);
	}
}
