package bf.emma.orderservice;

import bf.emma.orderservice.entities.Order;
import bf.emma.orderservice.entities.ProductItem;
import bf.emma.orderservice.entities.enums.OrderStatus;
import bf.emma.orderservice.entities.model.Customer;
import bf.emma.orderservice.entities.model.Product;
import bf.emma.orderservice.repositories.OrderRepository;
import bf.emma.orderservice.repositories.ProductItemRepository;
import bf.emma.orderservice.services.CustomerServiceClient;
import bf.emma.orderservice.services.InventoryServiceClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(OrderRepository orderRepository, ProductItemRepository productItemRepository,
                            CustomerServiceClient customerServiceClient, InventoryServiceClient inventoryServiceClient) {
        return args -> {
            List<Customer> customers = customerServiceClient.findAllCustomers().getContent().stream().toList();
            List<Product> products = inventoryServiceClient.findAll().getContent().stream().toList();
            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                Order order = Order.builder()
                        .customerId(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random()> 0.5 ? OrderStatus.PENDING : OrderStatus.CREATED)
                        .createdAt(new Date())
                        .build();
                Order savedOrder = orderRepository.save(order);
                for (int j = 0; j < products.size(); j++) {
                    if (Math.random() > 0.7 ) {
                        ProductItem productItem = ProductItem.builder()
                                .order(savedOrder)
                                .productId(products.get(j).getId())
                                .price(products.get(j).getPrice())
                                .quantity(1+random.nextInt(10))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);
                    }

                }
                
            }

        };
    }



}
