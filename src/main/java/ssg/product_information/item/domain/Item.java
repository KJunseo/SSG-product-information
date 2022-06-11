package ssg.product_information.item.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import ssg.product_information.promotion.domain.PromotionItem;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Integer itemPrice;

    private LocalDateTime itemDisplayStartDate;

    private LocalDateTime itemDisplayEndDate;

    @OneToMany(mappedBy = "item")
    private List<PromotionItem> promotionItems = new ArrayList<>();
}
