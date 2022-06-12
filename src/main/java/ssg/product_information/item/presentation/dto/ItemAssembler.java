package ssg.product_information.item.presentation.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.application.dto.response.ItemPromotionResponseDto;
import ssg.product_information.item.application.dto.response.ItemResponseDto;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemType;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;
import ssg.product_information.item.presentation.dto.response.ItemPromotionResponse;
import ssg.product_information.item.presentation.dto.response.ItemResponse;
import ssg.product_information.promotion.application.dto.PromotionAssembler;

import static ssg.product_information.common.Validator.validatesDateFormat;

public class ItemAssembler {

    private ItemAssembler() {
    }

    public static ItemCreateRequestDto itemCreateRequestDto(ItemCreateRequest request) {
        validatesDateFormat(request.getStart());
        validatesDateFormat(request.getEnd());
        return new ItemCreateRequestDto(
                request.getName(),
                ItemType.getType(request.getType()),
                request.getPrice(),
                LocalDate.parse(request.getStart(), DateTimeFormatter.ISO_DATE),
                LocalDate.parse(request.getEnd(), DateTimeFormatter.ISO_DATE)
        );
    }

    public static List<ItemResponse> itemResponses(List<ItemResponseDto> responseDto) {
        return responseDto.stream()
                          .map(dto -> new ItemResponse(
                                  dto.getId(),
                                  dto.getName(),
                                  dto.getType().getType(),
                                  dto.getPrice(),
                                  dto.getDisplayStartDate(),
                                  dto.getDisplayEndDate()
                          )).collect(Collectors.toList());
    }

    public static List<ItemResponseDto> itemResponseDtos(List<Item> items) {
        return items.stream()
                    .map(item -> new ItemResponseDto(
                            item.getId(),
                            item.getItemName(),
                            item.getItemType(),
                            item.getItemPrice(),
                            item.getItemDisplayStartDate(),
                            item.getItemDisplayEndDate()
                    )).collect(Collectors.toList());
    }

    public static ItemPromotionResponse itemPromotionResponse(ItemPromotionResponseDto responseDto) {
        return new ItemPromotionResponse(
                responseDto.getId(),
                responseDto.getName(),
                responseDto.getType(),
                responseDto.getOriginalPrice(),
                responseDto.getDiscountPrice(),
                responseDto.getDisplayStartDate(),
                responseDto.getDisplayEndDate(),
                PromotionAssembler.promotionResponse(responseDto.getPromotion())
        );
    }

    public static ItemPromotionResponseDto itemPromotionResponseDto(Item item) {
        return new ItemPromotionResponseDto(
                item.getId(),
                item.getItemName(),
                item.getItemType().getType(),
                item.getItemPrice(),
                item.getDiscountPrice(),
                item.getItemDisplayStartDate(),
                item.getItemDisplayEndDate(),
                PromotionAssembler.promotionResponseDto(item.getPromotion())
        );
    }
}
