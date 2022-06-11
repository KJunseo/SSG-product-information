package ssg.product_information.promotion.presentation;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.presentation.dto.ItemAssembler;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;
import ssg.product_information.promotion.application.PromotionService;
import ssg.product_information.promotion.application.dto.PromotionAssembler;
import ssg.product_information.promotion.application.dto.request.PromotionCreateRequestDto;
import ssg.product_information.promotion.presentation.dto.request.PromotionCreateRequest;

@RequestMapping("/promotions")
@RestController
public class PromotionRestController {
    private final PromotionService promotionService;

    public PromotionRestController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid PromotionCreateRequest request) {
        PromotionCreateRequestDto requestDto = PromotionAssembler.promotionCreateRequestDto(request);
        Long id = promotionService.create(requestDto);
        URI location = URI.create("/promotions/" + id);
        return ResponseEntity.created(location).build();
    }
}
