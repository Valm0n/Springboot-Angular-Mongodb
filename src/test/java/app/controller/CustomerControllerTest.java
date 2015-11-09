/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.Application;
import app.model.Customer;
import app.repository.CustomerRepository;

import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.util.NestedServletException;

/**
 *
 * @author vsimon
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class CustomerControllerTest {
    
    @Autowired
    private WebApplicationContext ctx;
    
    @Autowired
    private CustomerRepository repo;
    
    private MockMvc mvc;
    
    Customer han, luke, leia;
    
    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
        han = new Customer("Han", "Solo", "Corellia", "Smuggler", null);
        luke = new Customer("Luke", "Skywalker", "Tatooine", "Farmer", null);
        leia = new Customer("Leia", "Organa", "Alderaan", "Princess", null);
        repo.insert(Arrays.asList(han, luke, leia));
    }  
    
    @After
    public void cleanUp() {
        repo.delete(han);
        repo.delete(luke);
        repo.delete(leia);
    }
    
    @Test
    public void failToGetCustomersIfNotAllowed() throws Exception {
        setSecurityContext("anonymous");
        try {
            mvc.perform(get("/customer"));
        } catch(NestedServletException e){
            Throwable nestedException =  e.getCause();
            Assert.assertTrue(nestedException instanceof AccessDeniedException);
        }
    }
       
    @Test
    public void canDeleteCustomerIfAdmin()  throws Exception {
        setSecurityContext("admin");
        mvc.perform(delete("/customer/" + han.getId()))
                .andExpect(status().isOk());
        
        Assert.assertEquals(2, repo.count());
    }
    
    @Test
    public void canGetCustomersListIfAuthenticated() throws Exception {
        setSecurityContext("user");
        mvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
    
    
    /**
     * Allows to set up a user with specific authority
     * @param credential the value used as username, password and role
     */
    void setSecurityContext(String credential){
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(credential, credential, AuthorityUtils.createAuthorityList("ROLE_"+credential.toUpperCase()));
        SecurityContextHolder.getContext().setAuthentication(user);
    }
}
