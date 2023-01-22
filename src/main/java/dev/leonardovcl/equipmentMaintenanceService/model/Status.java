package dev.leonardovcl.equipmentMaintenanceService.model;

public class Status {

	private MaintenanceEmployee employee;
	
	private Stage stage;
	
	private String description;
	
	public enum Stage {
		RECEIVED, INITIADED, ONHOLD, RESUMED, FINISHED
	}

	public Status() {
		
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
