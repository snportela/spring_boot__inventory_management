package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.dtos.LoanDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    LoanDto loanToDto(Loan loan);

    Loan dtoToLoan(LoanDto loanDto);
}
