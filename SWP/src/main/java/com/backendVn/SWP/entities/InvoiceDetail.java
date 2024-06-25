package com.backendVn.SWP.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceDetailID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceID")
    private Invoice invoiceID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaterialID")
    private Material materialID;

    @Column(name = "TotalAmount")
    private Integer totalAmount;

    @Column(name = "TotalCost", precision = 18, scale = 2)
    private BigDecimal totalCost;

}