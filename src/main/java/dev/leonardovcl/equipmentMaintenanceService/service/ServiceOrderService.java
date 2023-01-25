package dev.leonardovcl.equipmentMaintenanceService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.Status;
import dev.leonardovcl.equipmentMaintenanceService.model.Status.Stage;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;

@Service
public class ServiceOrderService {

	@Autowired
	ServiceOrderRepository serviceOrderRepository;
	
	public Boolean isPending(List<Status> statusLog) {
		
		Long totalPending = statusLog
								.stream()
									.filter(status -> status.getStage() == Stage.FINISHED)
									.count();
		
		return totalPending == 0L;
	}
		
	public List<ServiceOrder> findByPendingStatus() {
		
		List<ServiceOrder> serviceOrdersFullList = serviceOrderRepository.findAll();
		
		List<ServiceOrder> serviceOrdersList = new ArrayList<>();
		
		serviceOrdersFullList.stream()
				.filter(servcieOrder -> isPending(servcieOrder.getStatusLog()))
				.forEach(servcieOrder -> serviceOrdersList.add(servcieOrder));
		
		return serviceOrdersList;
	}
	
	public Boolean isLastStage(List<Status> statusLog, Stage stage) {
		
		return statusLog.get(statusLog.size()-1).getStage() == stage;
	}
	
	public List<ServiceOrder> findByLastStatus(Stage stage) {
		
		List<ServiceOrder> serviceOrdersFullList = serviceOrderRepository.findAll();
		
		List<ServiceOrder> serviceOrdersList = new ArrayList<>();
		
		serviceOrdersFullList.stream()
				.filter(servcieOrder -> isLastStage(servcieOrder.getStatusLog(), stage))
				.forEach(servcieOrder -> serviceOrdersList.add(servcieOrder));
		
		return serviceOrdersList;
	}
}
