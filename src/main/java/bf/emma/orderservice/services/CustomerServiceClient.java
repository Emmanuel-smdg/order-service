package bf.emma.orderservice.services;

import bf.emma.orderservice.entities.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "customer-service")
public interface CustomerServiceClient{
    @GetMapping("/customers/{id}?projection=fullCustomer")
    Customer findCustomerById(@PathVariable Long id);

    @GetMapping("/customers?projection=fullCustomer")
    PagedModel<Customer> findAllCustomers();
}
