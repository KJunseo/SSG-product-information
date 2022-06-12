package ssg.product_information.user.domain.converter;

import java.util.EnumSet;
import javax.persistence.AttributeConverter;

import ssg.product_information.exception.user.NoSuchUserTypeException;
import ssg.product_information.user.domain.UserType;

public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType attribute) {
        return attribute.getType();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(UserType.class).stream()
                      .filter(e -> e.getType().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchUserTypeException::new);
    }
}
