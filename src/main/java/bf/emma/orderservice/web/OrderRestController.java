package bf.emma.orderservice.web;

import bf.emma.orderservice.entities.Order;
import bf.emma.orderservice.entities.model.Customer;
import bf.emma.orderservice.entities.model.Product;
import bf.emma.orderservice.repositories.OrderRepository;
import bf.emma.orderservice.repositories.ProductItemRepository;
import bf.emma.orderservice.services.CustomerServiceClient;
import bf.emma.orderservice.services.InventoryServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderRestController {

    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerServiceClient customerRestClientService;
    private InventoryServiceClient inventoryRestClientService;


    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order=orderRepository.findById(id).get();
        Customer customer=customerRestClientService.findCustomerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(pi->{
            Product product=inventoryRestClientService.findProductById(pi.getProductId());
            pi.setProduct(product);
        });
        return order;
    }
}
