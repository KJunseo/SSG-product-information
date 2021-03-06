package ssg.product_information.user.domain;

import javax.persistence.*;

import ssg.product_information.exception.user.NoSuchUserException;
import ssg.product_information.user.domain.converter.UserStatConverter;
import ssg.product_information.user.domain.converter.UserTypeConverter;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Convert(converter = UserTypeConverter.class)
    private UserType userType;

    @Convert(converter = UserStatConverter.class)
    private UserStat userStat;

    protected User() {
    }

    public User(String name, UserType type) {
        this(name, type, UserStat.NORMAL);
    }

    public User(String userName, UserType userType, UserStat userStat) {
        this.userName = userName;
        this.userType = userType;
        this.userStat = userStat;
    }

    public void withdrawal() {
        checkWithdrawal();
        this.userStat = UserStat.WITHDRAWAL;
    }

    public void checkWithdrawal() {
        if (UserStat.WITHDRAWAL.equals(this.userStat)) {
            throw new NoSuchUserException();
        }
    }

    public boolean isGeneralUser() {
        return UserType.GENERAL.equals(this.userType);
    }

    public boolean isEnterprise() {
        return UserType.ENTERPRISE.equals(this.userType);
    }

    public Long getId() {
        return id;
    }

    public UserStat getUserStat() {
        return userStat;
    }
}
