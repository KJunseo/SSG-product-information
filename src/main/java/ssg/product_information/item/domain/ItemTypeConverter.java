package ssg.product_information.item.domain;

import java.util.EnumSet;
import javax.persistence.AttributeConverter;

import ssg.product_information.exception.item.NoSuchItemTypeException;

public class ItemTypeConverter implements AttributeConverter<ItemType, String> {

    @Override
    public String convertToDatabaseColumn(ItemType attribute) {
        return attribute.getType();
    }

    @Override
    public ItemType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ItemType.class).stream()
                      .filter(e -> e.getType().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchItemTypeException::new);
    }
}
