package com.keyboard2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"itemOptions"})
@Table(name = "TBL_OPTION")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionKey;

    private String optionName;

    @ManyToOne
    @JoinColumn(name = "ITEM_KEY")
    private Item item;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ItemOption> itemOptions = new ArrayList<>();

}

