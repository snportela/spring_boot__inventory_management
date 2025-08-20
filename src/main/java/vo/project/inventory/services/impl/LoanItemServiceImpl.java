package vo.project.inventory.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.domain.LoanItem;
import vo.project.inventory.domain.LoanItemId;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.LoanItemCreateDto;
import vo.project.inventory.dtos.LoanItemResponseDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.LoanItemMapper;
import vo.project.inventory.repositories.LoanItemRepository;
import vo.project.inventory.repositories.LoanRepository;
import vo.project.inventory.repositories.ResourceRepository;
import vo.project.inventory.services.LoanItemService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LoanItemServiceImpl implements LoanItemService {

    private final LoanItemRepository loanItemRepository;
    private final LoanRepository loanRepository;
    private final ResourceRepository resourceRepository;
    private final LoanItemMapper loanItemMapper;

    public LoanItemServiceImpl(LoanItemRepository loanItemRepository, LoanRepository loanRepository, ResourceRepository resourceRepository, LoanItemMapper loanItemMapper) {
        this.loanItemRepository = loanItemRepository;
        this.loanRepository = loanRepository;
        this.resourceRepository = resourceRepository;
        this.loanItemMapper = loanItemMapper;
    }

    @Override
    public LoanItemResponseDto addItemToLoan(LoanItemCreateDto createDto) {
        Loan foundLoan = loanRepository.findById(createDto.loanId()).orElseThrow(() -> new NotFoundException(("Loan not found with ID: " + createDto.loanId())));
        Resource foundResource = resourceRepository.findById(createDto.resourceId()).orElseThrow(() -> new NotFoundException(("Resource not found with ID: " + createDto.resourceId())));

        if(loanItemRepository.existsByLoanIdAndResourceId(createDto.loanId(), createDto.resourceId())) {
            throw new AlreadyExistsException("Resource already exists in this loan.");
        }

        LoanItem saved = loanItemRepository.save(loanItemMapper.toEntity(createDto));

        return loanItemMapper.toLoanItemResponseDto(saved, foundLoan, foundResource);
    }

    @Override
    public List<LoanItemResponseDto> getItemsByLoanId(UUID loanId) {
        loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException(("Loan not found with ID: " + loanId)));

        List<Object[]> results = loanItemRepository.findByLoanId(loanId);

        return loanItemMapper.mapLoanItemQueryResultList(results);
    }

    @Override
    public List<LoanItemResponseDto> getItemsByResourceId(UUID resourceId) {
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException(("Resource not found with ID: " + resourceId)));

        List<Object[]> results = loanItemRepository.findByResourceId(resourceId);

        return loanItemMapper.mapLoanItemQueryResultList(results);
    }

    @Override
    public LoanItemResponseDto getLoanItem(UUID loanId, UUID resourceId) {
        LoanItemId id = new LoanItemId(loanId, resourceId);
        LoanItem item = loanItemRepository.findById(id)
                .filter(li -> li.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Loan Item not found,"));

        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException(("Loan not found with ID: " + loanId)));
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException(("Resource not found with ID: " + resourceId)));

        return loanItemMapper.toLoanItemResponseDto(item, loan, resource);
    }

    @Override
    public void removeItemFromLoan(UUID loanId, UUID resourceId) {
        if(!loanItemRepository.existsByLoanIdAndResourceId(loanId, resourceId)) {
            throw new NotFoundException("Loan Item not found.");
        }

        loanItemRepository.deleteByLoanIdAndResourceId(loanId, resourceId);
    }

    @Override
    public void removeAllItemsFromLoan(UUID loanId) {
        loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException(("Loan not found with ID: " + loanId)));
        loanItemRepository.deleteByLoanId(loanId);
    }
}
