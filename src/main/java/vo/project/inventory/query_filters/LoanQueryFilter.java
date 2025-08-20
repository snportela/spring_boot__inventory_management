package vo.project.inventory.query_filters;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.domain.enums.LoanStatus;

import java.time.Instant;

import static vo.project.inventory.specifications.LoanSpec.*;

@Data
public class LoanQueryFilter {

    private String studentName;

    private LoanStatus loanStatus;

    private Instant loanDate;

    public Specification<Loan> toSpecification() {
        return studentNameContains(studentName)
                .and(loanStatusEquals(loanStatus));
    }
}
