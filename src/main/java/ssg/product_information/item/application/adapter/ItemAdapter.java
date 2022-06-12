package ssg.product_information.item.application.adapter;

import java.util.List;

import ssg.product_information.item.domain.Item;
import ssg.product_information.user.domain.User;

public interface ItemAdapter {

    boolean supports(User user);

    List<Item> findItems();
}
