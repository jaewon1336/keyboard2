package com.keyboard2.entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"category","options","cartItems"})
@Table(name = "TBL_ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemKey;

    private String itemImage;

    private String itemName;

    private Integer itemPrice;

    private String itemDescription;

    private String switchType;

    private String color;


    @ManyToOne
    @JoinColumn(name = "CATEGORY_KEY")
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Image> images = new ArrayList<>();

}
