package dev.leonardovcl.equipmentMaintenanceService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
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
