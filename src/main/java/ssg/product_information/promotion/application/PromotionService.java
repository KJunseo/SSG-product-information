package ssg.product_information.promotion.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.product_information.item.application.ItemService;
import ssg.product_information.item.domain.Item;
import ssg.product_information.promotion.application.dto.request.PromotionCreateRequestDto;
import ssg.product_information.promotion.domain.Promotion;
import ssg.product_information.promotion.domain.PromotionRepository;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final ItemService itemService;

    public PromotionService(PromotionRepository promotionRepository, ItemService itemService) {
        this.promotionRepository = promotionRepository;
        this.itemService = itemService;
    }

    @Transactional
    public Long create(PromotionCreateRequestDto request) {
        Promotion promotion = new Promotion(
                request.getName(),
                request.getDiscountAmount(),
                request.getDiscountRate(),
                request.getStartDate(),
                request.getEndDate()
        );

        List<Item> items = itemService.findItemsByIds(request.getProducts());
        promotion.addItems(items);
        Promotion savedPromotion = promotionRepository.save(promotion);
        return savedPromotion.getId();
    }
}
