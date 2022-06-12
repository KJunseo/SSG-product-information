package ssg.product_information.user.domain.converter;

import java.util.EnumSet;
import javax.persistence.AttributeConverter;

import ssg.product_information.exception.user.NoSuchUserStatException;
import ssg.product_information.user.domain.UserStat;

public class UserStatConverter implements AttributeConverter<UserStat, String> {

    @Override
    public String convertToDatabaseColumn(UserStat attribute) {
        return attribute.getStat();
    }

    @Override
    public UserStat convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(UserStat.class).stream()
                      .filter(e -> e.getStat().equals(dbData))
                      .findAny()
                      .orElseThrow(NoSuchUserStatException::new);
    }
}
