package dev.leonardovcl.equipmentMaintenanceService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.Status;
import dev.leonardovcl.equipmentMaintenanceService.model.Status.Stage;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;

/**
* Holds methods to manipulate some repositories outcomes.
* 
* @author Leonardo Viana
*/
@Service
public class ServiceOrderService {

	@Autowired
	ServiceOrderRepository serviceOrderRepository;
	
	/**
	* Checks if a List of Status objects contains some Status with FINISHED stage.
	*
	* @param  statusLog  a list of Status Objects
	* @return if the statusLog contains a FINISHED stage
	*/
	public Boolean isPending(List<Status> statusLog) {
		
		Long totalPending = statusLog
								.stream()
									.filter(status -> status.getStage() == Stage.FINISHED)
									.count();
		
		return totalPending == 0L;
	}
	
	/**
	* Produce a List of Service Orders with no FINISHED stage.
	*
	* @return List of pending Service Order
	*/
	public List<ServiceOrder> findByPendingStatus() {
		
		List<ServiceOrder> serviceOrdersFullList = serviceOrderRepository.findAll();
		
		List<ServiceOrder> serviceOrdersList = new ArrayList<>();
		
		serviceOrdersFullList.stream()
				.filter(servcieOrder -> isPending(servcieOrder.getStatusLog()))
				.forEach(servcieOrder -> serviceOrdersList.add(servcieOrder));
		
		return serviceOrdersList;
	}
	
	/**
	* Checks if a List of Status objects contains last Status with stage.
	*
	* @param  statusLog  a list of Status Objects
	* @param  stage  a Stage Enum
	* @return if the statusLog contains stage in last Status
	*/
	public Boolean isLastStage(List<Status> statusLog, Stage stage) {
		
		return statusLog.get(statusLog.size()-1).getStage() == stage;
	}
	
	/**
	* Produce a List of Service Order that contains last Status with stage.
	*
	* @param  stage  a Stage Enum
	* @return List of Service Order that contains last Status with stage
	*/
	public List<ServiceOrder> findByLastStatus(Stage stage) {
		
		List<ServiceOrder> serviceOrdersFullList = serviceOrderRepository.findAll();
		
		List<ServiceOrder> serviceOrdersList = new ArrayList<>();
		
		serviceOrdersFullList.stream()
				.filter(servcieOrder -> isLastStage(servcieOrder.getStatusLog(), stage))
				.forEach(servcieOrder -> serviceOrdersList.add(servcieOrder));
		
		return serviceOrdersList;
	}
}
