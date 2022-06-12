package ssg.product_information.item.domain.discount;

import ssg.product_information.promotion.domain.Promotion;

public interface DiscountPolicy {

    boolean supports(Promotion promotion);

    int apply(Promotion promotion, int price);
}
