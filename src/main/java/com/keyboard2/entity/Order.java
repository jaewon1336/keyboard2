package com.keyboard2.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {})
@Table(name = "TBL_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderKey;

    @Column(name = "order_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @Builder.Default
    private LocalDate orderDate = LocalDate.now();

    private Integer orderTotalPrice;

    private String shippingAddress;

    private String orderName;

    private String orderPhone;

    @ManyToOne
    @JoinColumn(name = "USER_KEY")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
}
