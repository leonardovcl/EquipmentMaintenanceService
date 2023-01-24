package dev.leonardovcl.equipmentMaintenanceService.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class ServiceOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@NotNull(message = "Must not be Null!")
	private Customer customer;
	
	@OneToOne(mappedBy = "serviceOrder", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@NotNull(message = "Must not be Null!")
	private Equipment equipment;
	
	@OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL)
	private List<Status> statusLog;
	
	@Column(name = "problem_description")
	private String problemDescription;

	public ServiceOrder() {
		
	}

	public ServiceOrder(Long id, Customer customer, Equipment equipment, String problemDescription) {
		this.id = id;
		this.customer = customer;
		this.equipment = equipment;
		this.problemDescription = problemDescription;
	}

	public ServiceOrder(Long id, Customer customer, Equipment equipment, List<Status> statusLog, String problemDescription) {
		this.id = id;
		this.customer = customer;
		this.equipment = equipment;
		this.statusLog = statusLog;
		this.problemDescription = problemDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public List<Status> getStatusLog() {
		return statusLog;
	}

	public void setStatusLog(List<Status> statusLog) {
		this.statusLog = statusLog;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
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
		ServiceOrder other = (ServiceOrder) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "ServiceOrder [id=" + id + ", customer=" + customer + ", equipment=" + equipment + ", status="
				+ statusLog.get(statusLog.size() - 1) + "]";
	}
	
	
}
