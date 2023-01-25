package dev.leonardovcl.equipmentMaintenanceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;
import dev.leonardovcl.equipmentMaintenanceService.model.Equipment;
import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee;
import dev.leonardovcl.equipmentMaintenanceService.model.MaintenanceEmployee.Position;
import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.Status;
import dev.leonardovcl.equipmentMaintenanceService.model.Status.Stage;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.CustomerRepository;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.MaintenanceEmployeeRepository;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;

@SpringBootApplication
public class EquipmentMaintenanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentMaintenanceServiceApplication.class, args);
	}
	
	@Bean
	@Profile("default")
    public CommandLineRunner dataLoader(
    	CustomerRepository customerRepository,
    	MaintenanceEmployeeRepository maintenanceEmployeeRepository,
    	ServiceOrderRepository serviceOrderRepository
    ) {
        return args -> {
        	
        	Customer customer01 = new Customer("Adriana V.", "adrianav@gmail.com", "+5541999999901", "R. 01, n. 277");
        	Customer customer02 = new Customer("Ricardo L.", "ricardol@gmail.com", "+5541999999901", "R. 02, n. 100");
        	
        	customerRepository.save(customer01);
        	customerRepository.save(customer02);
        	
        	MaintenanceEmployee employee01 = new MaintenanceEmployee("Paulo H.", Position.LEADER);
        	MaintenanceEmployee employee02 = new MaintenanceEmployee("Gabiel H.", Position.ASSISTANT);
        	
        	maintenanceEmployeeRepository.save(employee01);
        	maintenanceEmployeeRepository.save(employee02);
        	
        	Equipment equipment01 = new Equipment("Compressor de Ar", "Vonder");
        	Equipment equipment02 = new Equipment("Esmerilhadeira Angular", "Bosh");
        	Equipment equipment03 = new Equipment("Furadeira", "Black&Decker");
        	
        	ServiceOrder serviceOrder01 = new ServiceOrder(customer01, equipment01, "Equipamento nao esta conseguindo realizar a compressao");
        	
        	List<Status> statusLog01 = new ArrayList<>(Arrays.asList(
        				new Status(serviceOrder01, employee02, Stage.RECEIVED, "Equipamento aguardando diagnostico"),
        				new Status(serviceOrder01, employee02, Stage.INITIATED, "Testes de vazamentos falharam, iniciando reparos na lataria"),
        				new Status(serviceOrder01, employee01, Stage.FINISHED, "Reparo finalizado, Equipamento em funcionamento normal")
        			));
        	
        	serviceOrder01.setStatusLog(statusLog01);
        	
        	ServiceOrder serviceOrder02 = new ServiceOrder(customer02, equipment02, "Equipamento nao liga");
        	
        	List<Status> statusLog02 = new ArrayList<>(Arrays.asList(
        				new Status(serviceOrder02, employee01, Stage.RECEIVED, "Equipamento aguardando diagnostico"),
        				new Status(serviceOrder02, employee02, Stage.INITIATED, "Testes de circuitos eletronicos falharam"),
        				new Status(serviceOrder02, employee02, Stage.ONHOLD, "Aguardando chegada de capacitor para substituicao, estimativa: 3 dias")
        			));
        	
        	serviceOrder02.setStatusLog(statusLog02);
        	
        	ServiceOrder serviceOrder03 = new ServiceOrder(customer02, equipment03, "Equipamento com avarias na carcaca");
        	
        	List<Status> statusLog03 = new ArrayList<>(Arrays.asList(
        				new Status(serviceOrder03, employee01, Stage.RECEIVED, "Equipamento aguardando diagnostico"),
        				new Status(serviceOrder03, employee01, Stage.INITIATED, "Danos irreparáveis na estrutura plastica"),
        				new Status(serviceOrder03, employee01, Stage.ONHOLD, "Aguardando chegada de nova carcaca, estimativa: 1 dia"),
        				new Status(serviceOrder03, employee01, Stage.RESUMED, "Realizando substituicao da carcaca plastica")
        			));
        	
        	serviceOrder03.setStatusLog(statusLog03);
        	
        	serviceOrderRepository.save(serviceOrder01);
        	serviceOrderRepository.save(serviceOrder02);
        	serviceOrderRepository.save(serviceOrder03);
        };
    }
}
