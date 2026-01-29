package com.pos.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.domain.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Order order;

    private String reason;

    private Double amount;

    @ManyToOne
    @JsonIgnore
    private ShiftReport shiftReport;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Branch branch;

    private PaymentType paymentType;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

}
