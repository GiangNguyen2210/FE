package com.backendVn.SWP.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private User customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_staffid")
    private User saleStaffid;

    @ColumnDefault("getdate()")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "recieved_at")
    private Instant recievedAt;

    @Column(name = "end_at")
    private Instant endAt;

    @Size(max = 50)
    @Nationalized
    @Column(name = "Status", length = 50)
    private String status;

    @Nationalized
    @Lob
    @Column(name = "Description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaterialID")
    private Material materialID;

    @Column(name = "material_weight", precision = 18, scale = 2)
    private BigDecimal materialWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_stone")
    private Material mainStone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_stone")
    private Material subStone;

    @Size(max = 50)
    @Nationalized
    @Column(name = "Category", length = 50)
    private String category;

    @Column(name = "produce_cost", precision = 18, scale = 2)
    private BigDecimal produceCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_design")
    private Design companyDesign;

    @Nationalized
    @Lob
    @Column(name = "URLImage")
    private String uRLImage;

    @Nationalized
    @Lob
    @Column(name = "denied_reason", nullable = true)
    private String deniedReason;

}