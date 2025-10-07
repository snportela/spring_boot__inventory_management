package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.LoanDto;
import vo.project.inventory.query_filters.LoanQueryFilter;
import vo.project.inventory.services.LoanService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listLoans(
            LoanQueryFilter filter, Pageable pageable
    ) {
        Map<String, Object> loanList = loanService.findAll(filter.toSpecification(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(loanList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable("id") UUID loanId) {
        LoanDto loan = loanService.findOne(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(loan);
    }

    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@Valid @RequestBody LoanDto loanDto) {
        LoanDto loan = loanService.save(loanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDto> updateLoan(
            @PathVariable("id") UUID loanId,
            @Valid @RequestBody LoanDto loanDto
    ) {
        LoanDto loan = loanService.update(loanId, loanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLoan(@PathVariable("id") UUID loanId) {
        loanService.delete(loanId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan with ID " + loanId + " was deleted successfully.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
