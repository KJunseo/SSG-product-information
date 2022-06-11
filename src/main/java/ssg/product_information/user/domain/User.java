package ssg.product_information.user.domain;

import javax.persistence.*;

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

    public User(String name, UserType type, UserStat stat) {
        this.userName = name;
        this.userType = type;
        this.userStat = stat;
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
