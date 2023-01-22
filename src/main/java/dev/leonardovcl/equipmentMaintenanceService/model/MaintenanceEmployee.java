package dev.leonardovcl.equipmentMaintenanceService.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class MaintenanceEmployee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Must not be Empty!")
	private String name;
	
	@NotNull(message = "Must not be Null!")
	private Position position;
	
	public enum Position {
		LEADER, PRINCIPAL, ASSISTANT
	}

	public MaintenanceEmployee() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaintenanceEmployee other = (MaintenanceEmployee) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "MaintenanceEmployee [id=" + id + ", name=" + name + ", position=" + position + "]";
	}
}
