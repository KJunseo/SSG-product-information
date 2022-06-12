package ssg.product_information.item.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.product_information.exception.item.NoSuchItemException;
import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.application.dto.response.ItemResponseDto;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemRepository;
import ssg.product_information.item.domain.ItemType;
import ssg.product_information.item.presentation.dto.ItemAssembler;
import ssg.product_information.user.application.UserService;
import ssg.product_information.user.domain.User;
import ssg.product_information.user.domain.UserStat;
import ssg.product_information.user.domain.UserType;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public ItemService(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
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

        if (user.isGeneralUser()) {
            List<Item> result = itemRepository.findAllByItemType(ItemType.GENERAL_MEMBERSHIP);
            return ItemAssembler.itemResponseDtos(result);
        }

        return ItemAssembler.itemResponseDtos(itemRepository.findAll());
    }
}
