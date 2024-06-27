package com.backendVn.SWP.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "request_order_detail")
public class RequestOrderDetail {
    @EmbeddedId
    private RequestOrderDetailId id;

    @MapsId("materialID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaterialID", nullable = false)
    private Material materialID;

    @MapsId("requestOrderid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_orderid", nullable = false)
    private RequestOrder requestOrderid;

    @Column(name = "Weight", precision = 18, scale = 2)
    private BigDecimal weight;

}