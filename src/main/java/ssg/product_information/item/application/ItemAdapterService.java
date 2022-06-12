package ssg.product_information.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import ssg.product_information.exception.user.NoSuchUserTypeException;
import ssg.product_information.item.application.adapter.ItemAdapter;
import ssg.product_information.user.domain.User;

@Service
public class ItemAdapterService {
    private final List<ItemAdapter> itemAdapters;

    public ItemAdapterService(List<ItemAdapter> itemAdapters) {
        this.itemAdapters = itemAdapters;
    }

    public ItemAdapter itemAdapterByUser(User user) {
        return itemAdapters.stream()
                           .filter(itemAdapter -> itemAdapter.supports(user))
                           .findFirst()
                           .orElseThrow(NoSuchUserTypeException::new);
    }
}
