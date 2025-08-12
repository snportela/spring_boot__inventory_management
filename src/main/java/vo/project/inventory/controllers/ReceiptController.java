package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.dtos.ReceiptDto;
import vo.project.inventory.mappers.ReceiptMapper;
import vo.project.inventory.services.ReceiptService;
import vo.project.inventory.specifications.ReceiptSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    private final ReceiptMapper receiptMapper;

    public ReceiptController(ReceiptService receiptService, ReceiptMapper receiptMapper) {
        this.receiptService = receiptService;
        this.receiptMapper = receiptMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listReceipts(
            @RequestParam(required = false) String supplier, Pageable pageable) {

        Page<Receipt> receiptList = receiptService.findAll(ReceiptSpec.supplierNameContains(supplier), pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", receiptList.getTotalElements());
        response.put("totalPages", receiptList.getTotalPages());
        response.put("receipts", receiptList.getContent().stream().map(receiptMapper::receiptToDto).collect(Collectors.toList()));
        response.put("currentPage", receiptList.getNumber());


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable("id") UUID receiptId) {
        Receipt foundReceipt = receiptService.findOne(receiptId);
        return ResponseEntity.status(HttpStatus.OK).body(receiptMapper.receiptToDto(foundReceipt));
    }

    @PostMapping
    public ResponseEntity<ReceiptDto> createReceipt(@Valid @RequestBody ReceiptDto receiptDto) {
        Receipt savedReceipt = receiptService.save(receiptMapper.dtoToReceipt(receiptDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(receiptMapper.receiptToDto(savedReceipt));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptDto> updateReceipt(
            @PathVariable("id") UUID receiptId,
            @Valid @RequestBody ReceiptDto receiptDto
    ) {
        Receipt updatedReceipt = receiptService.update(receiptId, receiptMapper.dtoToReceipt(receiptDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(receiptMapper.receiptToDto(updatedReceipt));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReceipt(@PathVariable("id") UUID receiptId) {
        receiptService.delete(receiptId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Receipt with ID: " + receiptId);
    }
}
