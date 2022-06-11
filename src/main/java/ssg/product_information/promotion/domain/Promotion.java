package ssg.product_information.promotion.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;

    private LocalDateTime promotionStartDate;

    private LocalDateTime promotionEndDate;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionItem> promotionItems = new ArrayList<>();

}
