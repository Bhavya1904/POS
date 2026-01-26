package com.pos.model;

import com.pos.domain.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private PaymentType paymentType;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }


}
