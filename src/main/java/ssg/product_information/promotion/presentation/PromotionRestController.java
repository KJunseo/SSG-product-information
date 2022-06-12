package ssg.product_information.promotion.presentation;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.ok().build();
    }

}
