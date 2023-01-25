package dev.leonardovcl.equipmentMaintenanceService.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.leonardovcl.equipmentMaintenanceService.model.Customer;
import dev.leonardovcl.equipmentMaintenanceService.model.Equipment;
import dev.leonardovcl.equipmentMaintenanceService.model.ServiceOrder;
import dev.leonardovcl.equipmentMaintenanceService.model.repository.ServiceOrderRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class ServiceOrderRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;
    
    @Test
    public void givenServiceOrdersByCustomer_shouldReturnServiceOrders_WhenFindByCustomerId() {
        
    	// given
    	Customer customer01 = new Customer("Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
    	entityManager.persist(customer01);
        entityManager.flush();
        
        Customer customer02 = new Customer("Teste02", "teste02@gmail.com", "+5541999999902", "R. 02");
    	entityManager.persist(customer02);
        entityManager.flush();
        
        Equipment equipment01 = new Equipment("Type01", "Brand01");
        Equipment equipment02 = new Equipment("Type02", "Brand02");
        Equipment equipment03 = new Equipment("Type03", "Brand03");
		
		ServiceOrder serviceOrder01 = new ServiceOrder(customer01, equipment01, "Problem Description01");
		equipment01.setServiceOrder(serviceOrder01);
        entityManager.persist(serviceOrder01);
        entityManager.flush();
        
        ServiceOrder serviceOrder02 = new ServiceOrder(customer01, equipment02, "Problem Description02");
        equipment02.setServiceOrder(serviceOrder02);
    	entityManager.persist(serviceOrder02);
        entityManager.flush();
        
        ServiceOrder serviceOrder03 = new ServiceOrder(customer02, equipment03, "Problem Description03");
        equipment03.setServiceOrder(serviceOrder03);
    	entityManager.persist(serviceOrder03);
        entityManager.flush();

        // when
        Page<ServiceOrder> serviceOrderPage = serviceOrderRepository.findByCustomerId(customer01.getId(), PageRequest.of(0, 5));

        // then
        assertThat(serviceOrderPage.getContent().size())
          .isEqualTo(2);
    }
    
    @Test
    public void givenNoServiceOrdersByCustomer_shouldReturnNoServiceOrders_WhenFindByCustomerId() {
        
    	// given
    	Customer customer01 = new Customer("Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
    	Long customerId = entityManager.persist(customer01).getId();
        entityManager.flush();

        // when
        Page<ServiceOrder> serviceOrderPage = serviceOrderRepository.findByCustomerId(customerId, PageRequest.of(0, 5));

        // then
        assertThat(serviceOrderPage.getContent().size())
          .isEqualTo(0);
    }
}
