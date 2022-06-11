package ssg.product_information.item.presentation;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssg.product_information.item.application.ItemService;
import ssg.product_information.item.application.dto.request.ItemCreateRequestDto;
import ssg.product_information.item.presentation.dto.ItemAssembler;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;

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
}
