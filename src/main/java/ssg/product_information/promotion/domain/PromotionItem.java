package ssg.product_information.promotion.domain;

import javax.persistence.*;

import ssg.product_information.item.domain.Item;

@Entity
public class PromotionItem {

    @EmbeddedId
    private PromotionItemId id;

    @MapsId(value = "promotionId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;

    @MapsId(value = "itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
