package ssg.product_information.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import ssg.product_information.exception.promotion.NoSuchPromotionException;
import ssg.product_information.exception.user.NoSuchUserTypeException;
import ssg.product_information.item.application.adapter.ItemAdapter;
import ssg.product_information.item.domain.discount.DiscountPolicy;
import ssg.product_information.promotion.domain.Promotion;
import ssg.product_information.user.domain.User;

@Service
public class ItemAdapterService {
    private final List<ItemAdapter> itemAdapters;
    private final List<DiscountPolicy> discountPolicies;

    public ItemAdapterService(List<ItemAdapter> itemAdapters, List<DiscountPolicy> discountPolicies) {
        this.itemAdapters = itemAdapters;
        this.discountPolicies = discountPolicies;
    }

    public ItemAdapter itemAdapterByUser(User user) {
        return itemAdapters.stream()
                           .filter(itemAdapter -> itemAdapter.supports(user))
                           .findFirst()
                           .orElseThrow(NoSuchUserTypeException::new);
    }

    public DiscountPolicy discountPolicyByPromotion(Promotion promotion) {
        return discountPolicies.stream()
                               .filter(policy -> policy.supports(promotion))
                               .findFirst()
                               .orElseThrow(NoSuchPromotionException::new);
    }
}
