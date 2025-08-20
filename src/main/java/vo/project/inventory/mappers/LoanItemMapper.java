package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.domain.LoanItem;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.LoanItemCreateDto;
import vo.project.inventory.dtos.LoanItemResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanItemMapper {

    LoanItem toEntity(LoanItemCreateDto createDto);

    @Mapping(source = "loanItem.loanId", target = "loanId")
    @Mapping(source = "loanItem.resourceId", target = "resourceId")
    @Mapping(source = "loan.studentName", target = "studentName")
    @Mapping(source = "loan.studentId", target = "studentId")
    @Mapping(source = "resource.name", target = "resourceName")
    LoanItemResponseDto toLoanItemResponseDto(LoanItem loanItem, Loan loan, Resource resource);

    default LoanItemResponseDto mapLoanItemQueryResult(Object[] result) {
        if (result == null || result.length != 3) {
            return null;
        }

        LoanItem loanItem = (LoanItem) result[0];
        Loan loan = (Loan) result[1];
        Resource resource = (Resource) result[2];

        return toLoanItemResponseDto(loanItem, loan, resource);
    }

    default List<LoanItemResponseDto> mapLoanItemQueryResultList(List<Object[]> results) {
        return results.stream()
                .map(this::mapLoanItemQueryResult)
                .collect(Collectors.toList());
    }
}

