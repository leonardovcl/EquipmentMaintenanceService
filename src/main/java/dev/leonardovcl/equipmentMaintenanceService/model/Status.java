package dev.leonardovcl.equipmentMaintenanceService.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Status {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JsonIgnore
//	@NotNull(message = "Must not be Null!")
	private ServiceOrder serviceOrder;
	
	@ManyToOne
	@NotNull(message = "Must not be Null!")
	private MaintenanceEmployee employee;
	
	@Column(name = "date_time", columnDefinition="TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date stageDateTime;
	
	@NotNull(message = "Must not be Null!")
	private Stage stage;
	
	@NotEmpty(message = "Must not be Empty!")
	private String description;
	
	public enum Stage {
		RECEIVED, INITIATED, ONHOLD, RESUMED, FINISHED
	}

	public Status() {
		this.stageDateTime = new Date();
	}
	
	public Status(ServiceOrder serviceOrder, MaintenanceEmployee employee, Stage stage, String description) {
		this.stageDateTime = new Date();
		this.serviceOrder = serviceOrder;
		this.employee = employee;
		this.stage = stage;
		this.description = description;
	}
	
	public Status(Long id, ServiceOrder serviceOrder, MaintenanceEmployee employee, Stage stage, String description) {
		this.stageDateTime = new Date();
		this.id = id;
		this.serviceOrder = serviceOrder;
		this.employee = employee;
		this.stage = stage;
		this.description = description;
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
	
	public Date getStageDateTime() {
		return stageDateTime;
	}

	public void setStageDateTime(Date stageDateTime) {
		this.stageDateTime = stageDateTime;
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
