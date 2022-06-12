package ssg.product_information.integration.promotion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.integration.IntegrationTest;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemRepository;
import ssg.product_information.item.domain.ItemType;
import ssg.product_information.promotion.application.PromotionService;
import ssg.product_information.promotion.domain.Promotion;
import ssg.product_information.promotion.domain.PromotionRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프로모션 통합 테스트")
public class PromotionIntegrationTest extends IntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionService promotionService;

    @Test
    @DisplayName("프로모션 삭제 테스트")
    void delete() {
        // given
        Item item
                = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 500, LocalDate.parse("2022-06-10"), LocalDate.parse("2022-08-31"));
        Item savedItem = itemRepository.save(item);

        Promotion promotion = new Promotion("쓱데이", 1000, LocalDate.parse("2022-06-10"), LocalDate.parse("2022-08-31"));
        promotion.addItems(List.of(savedItem));
        Promotion savedPromotion = promotionRepository.save(promotion);

        // when
        promotionService.delete(savedPromotion.getId());
        promotionRepository.flush();
        Optional<Promotion> result = promotionRepository.findById(savedPromotion.getId());

        // then
        assertThat(result).isEmpty();
    }
}
