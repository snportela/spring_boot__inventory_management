package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Loan;
import vo.project.inventory.dtos.LoanDto;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.LoanMapper;
import vo.project.inventory.repositories.LoanRepository;
import vo.project.inventory.services.LoanService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public LoanDto save(LoanDto loanDto) {
        Loan loan = loanRepository.save(loanMapper.dtoToLoan(loanDto));
        return loanMapper.loanToDto(loan);
    }

    @Override
    public Map<String, Object> findAll(Specification<Loan> spec, Pageable pageable) {
        Page<Loan> loanList = loanRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", loanList.getTotalElements());
        response.put("totalPages", loanList.getTotalPages());
        response.put("loans", loanList.getContent().stream().map(loanMapper::loanToDto).collect(Collectors.toList()));
        response.put("currentPage", loanList.getNumber());

        return response;
    }

    @Override
    public LoanDto findOne(UUID loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Could not find Loan with ID: " + loanId));
        return loanMapper.loanToDto(loan);
    }

    @Override
    public LoanDto update(UUID loanId, LoanDto loanDto) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Could not find Loan with ID: " + loanId));

        loan.setStudentName(loanDto.studentName());
        loan.setStudentId(loanDto.studentId());
        loan.setLoanDate(loanDto.loanDate());
        loan.setLoanStatus(loanDto.loanStatus());
        loan.setObservation(loanDto.observation());

        loanRepository.save(loan);

        return loanMapper.loanToDto(loan);
    }

    @Override
    public void delete(UUID loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Could not find Loan with ID: " + loanId));
        loanRepository.delete(loan);
    }
}
