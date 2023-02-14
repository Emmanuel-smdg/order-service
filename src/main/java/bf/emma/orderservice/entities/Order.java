package bf.emma.orderservice.entities;

import bf.emma.orderservice.entities.enums.OrderStatus;
import bf.emma.orderservice.entities.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private Date createdAt ;
    private OrderStatus status;
    private Long customerId;

    @Transient
    private Customer customer ;

    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItems;
}
