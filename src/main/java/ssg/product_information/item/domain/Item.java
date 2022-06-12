package ssg.product_information.item.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.*;

import ssg.product_information.exception.item.InPromotionException;
import ssg.product_information.exception.item.ItemDisplayPeriodException;
import ssg.product_information.exception.promotion.PromotionItemDisplayPeriodException;
import ssg.product_information.item.domain.discount.DiscountPolicy;
import ssg.product_information.promotion.domain.Promotion;
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

    private LocalDate itemDisplayStartDate;

    private LocalDate itemDisplayEndDate;

    @OneToMany(mappedBy = "item")
    private List<PromotionItem> promotionItems = new ArrayList<>();

    @Transient
    private Promotion promotion;

    @Transient
    private Integer discountPrice;

    protected Item() {
    }

    public Item(
            String itemName,
            ItemType itemType,
            Integer itemPrice,
            LocalDate itemDisplayStartDate,
            LocalDate itemDisplayEndDate
    ) {
        validatesDisplayPeriod(itemDisplayStartDate, itemDisplayEndDate);
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemDisplayStartDate = itemDisplayStartDate;
        this.itemDisplayEndDate = itemDisplayEndDate;
    }

    private void validatesDisplayPeriod(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new ItemDisplayPeriodException();
        }
    }

    public void checkDisplayPeriod(LocalDate startDate, LocalDate endDate) {
        if (itemDisplayStartDate.isAfter(startDate) || itemDisplayEndDate.isBefore(endDate)) {
            throw new PromotionItemDisplayPeriodException();
        }
    }

    public void checkInPromotion() {
        if (!promotionItems.isEmpty()) {
            throw new InPromotionException();
        }
    }

    public boolean isDisplay() {
        LocalDate now = LocalDate.now();
        return !(now.isBefore(this.itemDisplayStartDate) || now.isAfter(this.itemDisplayEndDate));
    }

    public List<Promotion> getAllPromotion() {
        return promotionItems.stream()
                             .map(PromotionItem::getPromotion)
                             .collect(Collectors.toList());
    }

    public void discount(DiscountPolicy discountPolicy, Promotion promotion) {
        int discountPrice = discountPolicy.apply(promotion, this.itemPrice);
        if (isMinPrice(discountPrice) && discountPrice > 0) {
            this.discountPrice = discountPrice;
            this.promotion = promotion;
        }
    }

    private boolean isMinPrice(int discountPrice) {
        return Objects.isNull(this.discountPrice) || (discountPrice < this.discountPrice);
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public LocalDate getItemDisplayStartDate() {
        return itemDisplayStartDate;
    }

    public LocalDate getItemDisplayEndDate() {
        return itemDisplayEndDate;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public List<PromotionItem> getPromotionItems() {
        return promotionItems;
    }
}
