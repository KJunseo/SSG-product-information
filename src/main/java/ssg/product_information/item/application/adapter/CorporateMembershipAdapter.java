package ssg.product_information.item.application.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemRepository;
import ssg.product_information.user.domain.User;

@Component
public class CorporateMembershipAdapter implements ItemAdapter {
    private final ItemRepository itemRepository;

    public CorporateMembershipAdapter(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean supports(User user) {
        return user.isEnterprise();
    }

    @Override
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
