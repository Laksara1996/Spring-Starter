package com.amigotutorial.springbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class SpringBootExampleApplication {

    private final CustomerRepository customerRepository;

    public SpringBootExampleApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringBootExampleApplication.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age) {
    }

    @PostMapping
    public void addCustomer(@RequestBody  NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);

        customerRepository.save(customer);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@RequestBody NewCustomerRequest request, @PathVariable("customerId") Integer id){
        Customer customer = customerRepository.findById(id).orElse(new Customer());
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);

        customerRepository.save(customer);

    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

//    @GetMapping("/greet")
//    public GreetResponse greet() {
//        return new GreetResponse("Hello!!");
//    }
//
//    record GreetResponse(String greet){}

}
