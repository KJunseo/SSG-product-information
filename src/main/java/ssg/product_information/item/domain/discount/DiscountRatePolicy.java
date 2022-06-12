package ssg.product_information.item.domain.discount;

import org.springframework.stereotype.Component;

import ssg.product_information.promotion.domain.Promotion;

@Component
public class DiscountRatePolicy implements DiscountPolicy {

    @Override
    public boolean supports(Promotion promotion) {
        return promotion.isDiscountRate();
    }

    @Override
    public int apply(Promotion promotion, int price) {
        return price - (int) Math.ceil(price * promotion.getDiscountRate());
    }
}
