package ssg.product_information.item.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import ssg.product_information.exception.item.ItemDisplayPeriodException;
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

    public List<PromotionItem> getPromotionItems() {
        return promotionItems;
    }
}
