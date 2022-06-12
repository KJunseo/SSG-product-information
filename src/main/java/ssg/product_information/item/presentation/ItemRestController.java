package ssg.product_information.item.presentation;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ssg.product_information.item.application.ItemService;
import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.application.dto.response.ItemPromotionResponseDto;
import ssg.product_information.item.application.dto.response.ItemResponseDto;
import ssg.product_information.item.presentation.dto.ItemAssembler;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;
import ssg.product_information.item.presentation.dto.response.ItemPromotionResponse;
import ssg.product_information.item.presentation.dto.response.ItemResponse;

@RequestMapping("/items")
@RestController
public class ItemRestController {
    private final ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ItemCreateRequest request) {
        ItemCreateRequestDto requestDto = ItemAssembler.itemCreateRequestDto(request);
        Long id = itemService.create(requestDto);
        URI location = URI.create("/items/" + id);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/purchase")
    public ResponseEntity<List<ItemResponse>> itemsByUser(@RequestParam("userId") Long id) {
        List<ItemResponseDto> responseDto = itemService.findItemsByUserId(id);
        List<ItemResponse> result = ItemAssembler.itemResponses(responseDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/promotions")
    public ResponseEntity<ItemPromotionResponse> itemPromotion(@PathVariable Long id) {
        ItemPromotionResponseDto responseDto = itemService.findItemPromotionByItemId(id);
        ItemPromotionResponse result = ItemAssembler.itemPromotionResponse(responseDto);
        return ResponseEntity.ok(result);
    }
}
