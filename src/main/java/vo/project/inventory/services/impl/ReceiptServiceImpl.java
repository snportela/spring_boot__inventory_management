package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.dtos.ReceiptDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.ReceiptMapper;
import vo.project.inventory.repositories.ReceiptRepository;
import vo.project.inventory.services.ReceiptService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
    }

    @Override
    public ReceiptDto save(ReceiptDto receiptDto) {
        if(receiptRepository.existsReceiptByReceiptNumber(receiptDto.receiptNumber())) {
            throw new AlreadyExistsException("A Receipt with this number already exists.");
        }
        Receipt receipt = receiptRepository.save(receiptMapper.dtoToReceipt(receiptDto));
        return receiptMapper.receiptToDto(receipt);
    }

    @Override
    public Map<String, Object> findAll(Specification<Receipt> spec, Pageable pageable) {
        Page<Receipt> receiptList = receiptRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", receiptList.getTotalElements());
        response.put("totalPages", receiptList.getTotalPages());
        response.put("receipts", receiptList.getContent().stream().map(receiptMapper::receiptToDto).collect(Collectors.toList()));
        response.put("currentPage", receiptList.getNumber());

        return response;
    }

    @Override
    public ReceiptDto findOne(UUID receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));
        return receiptMapper.receiptToDto(receipt);
    }

    @Override
    public ReceiptDto update(UUID receiptId, ReceiptDto receiptDto) {
        Optional<Receipt> duplicate = receiptRepository.findReceiptByReceiptNumber(receiptDto.receiptNumber());

        if(duplicate.isPresent() && !duplicate.get().getReceiptId().equals(receiptId)) {
            throw new AlreadyExistsException("A Receipt with this number already exists.");
        }

        Receipt foundReceipt = receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));

        foundReceipt.setReceiptNumber(receiptDto.receiptNumber());
        foundReceipt.setAccessKey(receiptDto.accessKey());
        foundReceipt.setPrice(receiptDto.price());
        foundReceipt.setSupplier(receiptDto.supplier());
        foundReceipt.setReceiptDate(receiptDto.receiptDate());

        receiptRepository.save(foundReceipt);

        return receiptMapper.receiptToDto(foundReceipt);
    }

    @Override
    public void delete(UUID receiptId) {
        receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));
        receiptRepository.deleteById(receiptId);
    }
}
