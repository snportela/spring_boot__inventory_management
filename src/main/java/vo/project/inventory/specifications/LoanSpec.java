package vo.project.inventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.domain.enums.LoanStatus;

public class LoanSpec {

    public static Specification<Loan> studentNameContains(String studentName) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(studentName)) return null;

            return builder.like(builder.lower(root.get("studentName")), "%" + studentName.toLowerCase() + "%");
        };
    }

    public static Specification<Loan> loanStatusEquals(LoanStatus loanStatus) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(loanStatus)) return null;

            return builder.equal(root.get("loanStatus"), loanStatus);
        };
    }


}
