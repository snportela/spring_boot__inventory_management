package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.ReceiptDto;
import vo.project.inventory.services.ReceiptService;
import vo.project.inventory.specifications.ReceiptSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;


    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listReceipts(
            @RequestParam(required = false) String supplier, Pageable pageable) {

        Map<String, Object> response = receiptService.findAll(ReceiptSpec.supplierNameContains(supplier), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable("id") UUID receiptId) {
        ReceiptDto foundReceipt = receiptService.findOne(receiptId);
        return ResponseEntity.status(HttpStatus.OK).body(foundReceipt);
    }

    @PostMapping
    public ResponseEntity<ReceiptDto> createReceipt(@Valid @RequestBody ReceiptDto receiptDto) {
        ReceiptDto savedReceipt = receiptService.save(receiptDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReceipt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptDto> updateReceipt(
            @PathVariable("id") UUID receiptId,
            @Valid @RequestBody ReceiptDto receiptDto
    ) {
        ReceiptDto updatedReceipt = receiptService.update(receiptId, receiptDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedReceipt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReceipt(@PathVariable("id") UUID receiptId) {
        receiptService.delete(receiptId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Receipt with ID " + receiptId + " was deleted successfully.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
