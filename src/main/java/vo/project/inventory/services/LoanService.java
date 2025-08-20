package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.dtos.LoanDto;

import java.util.Map;
import java.util.UUID;

public interface LoanService {

    LoanDto save(LoanDto loanDto);

    Map<String, Object> findAll(Specification<Loan> spec, Pageable pageable);

    LoanDto findOne(UUID loanId);

    LoanDto update(UUID loanId, LoanDto loanDto);

    void delete(UUID loanId);
}
