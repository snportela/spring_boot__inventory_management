package vo.project.inventory.services;

import vo.project.inventory.dtos.LoanItemCreateDto;
import vo.project.inventory.dtos.LoanItemResponseDto;

import java.util.List;
import java.util.UUID;

public interface LoanItemService {

    LoanItemResponseDto addItemToLoan(LoanItemCreateDto createDto);

    List<LoanItemResponseDto> getItemsByLoanId(UUID loanId);

    List<LoanItemResponseDto> getItemsByResourceId(UUID resourceId);

    LoanItemResponseDto getLoanItem(UUID loanId, UUID resourceId);

    void removeItemFromLoan(UUID loanId, UUID resourceId);

    void removeAllItemsFromLoan(UUID loanId);
}
