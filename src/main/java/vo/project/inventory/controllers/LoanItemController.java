package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.LoanItemCreateDto;
import vo.project.inventory.dtos.LoanItemResponseDto;
import vo.project.inventory.services.LoanItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/loan-items")
public class LoanItemController {

    private final LoanItemService loanItemService;

    public LoanItemController(LoanItemService loanItemService) {
        this.loanItemService = loanItemService;
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<LoanItemResponseDto>> getItemsByLoanId(@PathVariable("loanId") UUID loanId) {
        List<LoanItemResponseDto> items = loanItemService.getItemsByLoanId(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<LoanItemResponseDto>> getItemsByResourceId(@PathVariable("resourceId") UUID resourceId) {
        List<LoanItemResponseDto> items = loanItemService.getItemsByResourceId(resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/{loanId}/{resourceId}")
    public ResponseEntity<LoanItemResponseDto> getLoanItem(
            @PathVariable("loanId") UUID loanId,
            @PathVariable("resourceId") UUID resourceId
    ) {
        LoanItemResponseDto item = loanItemService.getLoanItem(loanId, resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PostMapping
    public ResponseEntity<LoanItemResponseDto> addItemToLoan(@Valid @RequestBody LoanItemCreateDto createDto) {
        LoanItemResponseDto response = loanItemService.addItemToLoan(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{loanId}/{resourceId}")
    public ResponseEntity<Object> removeItemFromLoan(
            @PathVariable("loanId") UUID loanId,
            @PathVariable("resourceId") UUID resourceId
    ) {
        loanItemService.removeItemFromLoan(loanId, resourceId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Deleted Resource with ID: " + resourceId + " in Loan with ID: " + loanId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/loan/{loanId}")
    public ResponseEntity<Object> removeAllItemsFromLoan(@PathVariable("loanId") UUID loanId) {
        loanItemService.removeAllItemsFromLoan(loanId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan with ID " + loanId + " was deleted successfully.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
