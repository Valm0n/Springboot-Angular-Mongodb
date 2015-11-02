package app.controller;

import app.model.Customer;
import app.repository.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private CustomerRepository repository;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer){
        repository.save(customer);
        return customer;
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")    
    @RequestMapping(method = RequestMethod.GET, value="/{customerId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("customerId") String customerId){
        return repository.findOne(customerId);
    }
        
    @PreAuthorize("hasRole('ROLE_USER')")    
    @RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllCustomers(){
        return repository.findAll();
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    @RequestMapping(method = RequestMethod.DELETE, value="/{customerId}")
    public void removeCustomer(@PathVariable("customerId") String customerId){
        repository.delete(customerId);
    }
}
