package ssg.product_information.item.presentation.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.domain.ItemType;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;

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
}
