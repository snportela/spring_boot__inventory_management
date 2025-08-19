package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.ReceiptItemCreateDto;
import vo.project.inventory.dtos.ReceiptItemResponseDto;
import vo.project.inventory.dtos.ReceiptItemUpdateDto;
import vo.project.inventory.services.ReceiptItemService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/receipt-items")
public class ReceiptItemController {

    private final ReceiptItemService receiptItemService;

    public ReceiptItemController(ReceiptItemService receiptItemService) {
        this.receiptItemService = receiptItemService;
    }

    @GetMapping("/receipt/{receiptId}")
    public ResponseEntity<List<ReceiptItemResponseDto>> getItemsByReceipt(@PathVariable UUID receiptId) {
        List<ReceiptItemResponseDto> items = receiptItemService.getItemsByReceiptId(receiptId);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<ReceiptItemResponseDto>> getItemsByResource(@PathVariable UUID resourceId) {
        List<ReceiptItemResponseDto> items = receiptItemService.getItemsByResourceId(resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/{receiptId}/{resourceId}")
    public ResponseEntity<ReceiptItemResponseDto> getReceiptItem(
            @PathVariable UUID receiptId,
            @PathVariable UUID resourceId) {
        ReceiptItemResponseDto item = receiptItemService.getReceiptItem(receiptId, resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PostMapping
    public ResponseEntity<ReceiptItemResponseDto> addItemToReceipt(@Valid @RequestBody ReceiptItemCreateDto createDto) {
        ReceiptItemResponseDto response = receiptItemService.addItemToReceipt(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{receiptId}/{resourceId}")
    public ResponseEntity<ReceiptItemResponseDto> updateReceiptItem(
            @PathVariable UUID receiptId,
            @PathVariable UUID resourceId,
            @Valid @RequestBody ReceiptItemUpdateDto updateDTO) {
        ReceiptItemResponseDto updated = receiptItemService.updateReceiptItem(receiptId, resourceId, updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{receiptId}/{resourceId}")
    public ResponseEntity<String> removeItemFromReceipt(
            @PathVariable UUID receiptId,
            @PathVariable UUID resourceId) {
        receiptItemService.removeItemFromReceipt(receiptId, resourceId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Resource with ID: " + resourceId + " in Receipt with ID: " + receiptId);
    }

    @DeleteMapping("/receipt/{receiptId}")
    public ResponseEntity<String> removeAllItemsFromReceipt(@PathVariable UUID receiptId) {
        receiptItemService.removeAllItemsFromReceipt(receiptId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted all Resources from Receipt with ID: " + receiptId);
    }

}
