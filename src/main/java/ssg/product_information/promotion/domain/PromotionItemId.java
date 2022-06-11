package ssg.product_information.promotion.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class PromotionItemId implements Serializable {

    private Long promotionId;

    private Long itemId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PromotionItemId))
            return false;
        PromotionItemId that = (PromotionItemId) o;
        return Objects.equals(promotionId, that.promotionId) && Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotionId, itemId);
    }
}
