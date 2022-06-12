package ssg.product_information.item.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.product_information.exception.item.NoSuchItemException;
import ssg.product_information.item.application.adapter.ItemAdapter;
import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.application.dto.response.ItemPromotionResponseDto;
import ssg.product_information.item.application.dto.response.ItemResponseDto;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemRepository;
import ssg.product_information.item.domain.discount.DiscountPolicy;
import ssg.product_information.item.presentation.dto.ItemAssembler;
import ssg.product_information.user.application.UserService;
import ssg.product_information.user.domain.User;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemAdapterService itemAdapterService;

    public ItemService(ItemRepository itemRepository, UserService userService, ItemAdapterService itemAdapterService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemAdapterService = itemAdapterService;
    }

    @Transactional
    public Long create(ItemCreateRequestDto request) {
        Item item = new Item(request.getName(), request.getType(), request.getPrice(), request.getStart(), request.getEnd());
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    @Transactional(readOnly = true)
    public List<Item> findItemsByIds(List<Long> products) {
        return itemRepository.findAllById(products);
    }

    @Transactional
    public void delete(Long id) {
        Item item = findById(id);
        item.checkInPromotion();
        itemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return itemRepository.findById(id)
                             .orElseThrow(NoSuchItemException::new);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDto> findItemsByUserId(Long id) {
        User user = userService.findById(id);
        user.checkWithdrawal();

        ItemAdapter itemAdapter = itemAdapterService.itemAdapterByUser(user);
        List<Item> items = itemAdapter.findItems()
                                      .stream()
                                      .filter(Item::isDisplay)
                                      .collect(Collectors.toList());

        return ItemAssembler.itemResponseDtos(items);
    }

    @Transactional(readOnly = true)
    public ItemPromotionResponseDto findItemPromotionByItemId(Long id) {
        Item item = findById(id);

        item.getAllPromotion()
            .forEach(promotion -> {
                DiscountPolicy discountPolicy = itemAdapterService.discountPolicyByPromotion(promotion);
                item.discount(discountPolicy, promotion);
            });

        return ItemAssembler.itemPromotionResponseDto(item);
    }
}
