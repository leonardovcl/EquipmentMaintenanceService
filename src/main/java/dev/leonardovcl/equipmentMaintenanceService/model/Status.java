package dev.leonardovcl.equipmentMaintenanceService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Status {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@NotNull(message = "Must not be Null!")
	private ServiceOrder serviceOrder;
	
	@ManyToOne
	@NotNull(message = "Must not be Null!")
	private MaintenanceEmployee employee;
	
	@NotNull(message = "Must not be Null!")
	private Stage stage;
	
	@NotEmpty(message = "Must not be Empty!")
	private String description;
	
	public enum Stage {
		RECEIVED, INITIADED, ONHOLD, RESUMED, FINISHED
	}

	public Status() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}



	public MaintenanceEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(MaintenanceEmployee employee) {
		this.employee = employee;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
