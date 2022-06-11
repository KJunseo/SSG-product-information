package ssg.product_information.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Long create(ItemCreateRequestDto request) {
        Item item = new Item(request.getName(), request.getType(), request.getPrice(), request.getStart(), request.getEnd());
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }
}
