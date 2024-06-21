package com.janis.customer.customer;

import com.janis.customer.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customer) {
        var customerEntity = customerRepository.findById(customer.id()).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerEntity.setFirstName(customer.firstName());
        customerEntity.setLastName(customer.lastName());
        customerEntity.setAddress(customer.address());
        customerEntity.setEmail(customer.email());
        customerRepository.save(customerEntity);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toCustomerResponse).toList();
    }

    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    public CustomerResponse findCustomerById(String customerId) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return customerMapper.toCustomerResponse(customer);
    }

    public void deleteCustomerById(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
