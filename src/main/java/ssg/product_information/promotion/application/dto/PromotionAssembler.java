package ssg.product_information.promotion.application.dto;

import java.time.LocalDate;

import ssg.product_information.promotion.application.dto.request.PromotionCreateRequestDto;
import ssg.product_information.promotion.presentation.dto.request.PromotionCreateRequest;

import static ssg.product_information.common.Validator.validatesDateFormat;

public class PromotionAssembler {

    private PromotionAssembler() {
    }

    public static PromotionCreateRequestDto promotionCreateRequestDto(PromotionCreateRequest request) {
        validatesDateFormat(request.getStartDate());
        validatesDateFormat(request.getEndDate());
        return new PromotionCreateRequestDto(
                request.getName(),
                request.getDiscountAmount(),
                request.getDiscountRate(),
                LocalDate.parse(request.getStartDate()),
                LocalDate.parse(request.getEndDate()),
                request.getProducts()
        );
    }
}
