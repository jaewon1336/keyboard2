package com.keyboard2.entity;
import lombok.*;

import javax.persistence.*;
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {})
@Table(name = "TBL_IMAGE")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageKey;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "ITEM_KEY")
    private Item item;


}

