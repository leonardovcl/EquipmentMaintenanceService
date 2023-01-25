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
import dev.leonardovcl.equipmentMaintenanceService.model.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Test
    public void givenCustomersWithNameLike_shouldReturnCustomers_WhenFindByNameLike() {
        
    	// given
    	Customer customer01 = new Customer("Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
    	entityManager.persist(customer01);
        entityManager.flush();
        
        Customer customer02 = new Customer("Teste02", "teste02@gmail.com", "+5541999999902", "R. 02");
    	entityManager.persist(customer02);
        entityManager.flush();
        
        Customer customer03 = new Customer("NoName", "teste03@gmail.com", "+5541999999903", "R. 03");
    	entityManager.persist(customer03);
        entityManager.flush();

        // when
        Page<Customer> customerPage = customerRepository.findByNameContainingIgnoreCase("teste", PageRequest.of(0, 5));

        // then
        assertThat(customerPage.getContent().size())
          .isEqualTo(2);
    }
    
    @Test
    public void givenNoustomersWithNameLike_shouldReturnNoCustomerss_WhenFindByNameLike() {
        
    	// given
    	Customer customer01 = new Customer("Teste01", "teste01@gmail.com", "+5541999999901", "R. 01");
    	entityManager.persist(customer01);
        entityManager.flush();
        
        Customer customer02 = new Customer("Teste02", "teste02@gmail.com", "+5541999999902", "R. 02");
    	entityManager.persist(customer02);
        entityManager.flush();
        
        Customer customer03 = new Customer("Teste03", "teste03@gmail.com", "+5541999999903", "R. 03");
    	entityManager.persist(customer03);
        entityManager.flush();

        // when
        Page<Customer> customerPage = customerRepository.findByNameContainingIgnoreCase("name", PageRequest.of(0, 5));

        // then
        assertThat(customerPage.getContent().size())
          .isEqualTo(0);
    }
}
