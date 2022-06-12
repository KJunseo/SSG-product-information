package ssg.product_information.promotion.application.dto;

import java.time.LocalDate;

import ssg.product_information.promotion.application.dto.request.PromotionCreateRequestDto;
import ssg.product_information.promotion.application.dto.response.PromotionResponseDto;
import ssg.product_information.promotion.domain.Promotion;
import ssg.product_information.promotion.presentation.dto.request.PromotionCreateRequest;
import ssg.product_information.promotion.presentation.dto.response.PromotionResponse;

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

    public static PromotionResponseDto promotionResponseDto(Promotion promotion) {
        return new PromotionResponseDto(
                promotion.getId(),
                promotion.getPromotionName(),
                promotion.getDiscountAmount(),
                promotion.getDiscountRate(),
                promotion.getPromotionStartDate(),
                promotion.getPromotionEndDate()
        );
    }

    public static PromotionResponse promotionResponse(PromotionResponseDto promotion) {
        return new PromotionResponse(
                promotion.getId(),
                promotion.getName(),
                promotion.getDiscountAmount(),
                promotion.getDiscountRate(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );
    }
}
