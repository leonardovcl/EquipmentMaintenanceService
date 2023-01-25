package dev.leonardovcl.equipmentMaintenanceService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Equipment {

	@Id
    @Column(name = "serviceOrder_id")
    private Long id;
	
	@OneToOne
	@MapsId
    @JoinColumn(name = "serviceOrder_id")
	@JsonIgnore
//	@NotNull(message = "Must not be Null!")
	private ServiceOrder serviceOrder;
	
	@NotEmpty(message = "Must not be Empty!")
	private String type;
	
	@NotEmpty(message = "Must not be Empty!")
	private String brand;
	
	private String observations;

	public Equipment() {
		
	}
	
	public Equipment(String type, String brand) {
		this.type = type;
		this.brand = brand;
	}
	
	public Equipment(Long id, String type, String brand) {
		this.id = id;
		this.type = type;
		this.brand = brand;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}	
}
