package ssg.product_information.integration.item;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.integration.IntegrationTest;
import ssg.product_information.item.application.ItemService;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemRepository;
import ssg.product_information.item.domain.ItemType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 통합 테스트")
public class ItemIntegrationTest extends IntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete() {
        // given
        Item item
                = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 500, LocalDate.parse("2022-06-10"), LocalDate.parse("2022-08-31"));
        Item savedItem = itemRepository.save(item);

        // when
        itemService.delete(savedItem.getId());
        itemRepository.flush();
        Optional<Item> result = itemRepository.findById(savedItem.getId());

        // then
        assertThat(result).isEmpty();
    }
}
