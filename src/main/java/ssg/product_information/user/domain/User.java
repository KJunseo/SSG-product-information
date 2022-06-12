package ssg.product_information.user.domain;

import javax.persistence.*;

import ssg.product_information.exception.user.AlreadyWithdrawalException;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
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
        if (UserStat.WITHDRAWAL.equals(this.userStat)) {
            throw new AlreadyWithdrawalException();
        }
        this.userStat = UserStat.WITHDRAWAL;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserStat getUserStat() {
        return userStat;
    }
}
