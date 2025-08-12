package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.ReceiptRepository;
import vo.project.inventory.services.ReceiptService;

import java.util.UUID;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public Receipt save(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    @Override
    public Page<Receipt> findAll(Specification<Receipt> spec, Pageable pageable) {
        return receiptRepository.findAll(spec, pageable);
    }

    @Override
    public Receipt findOne(UUID receiptId) {
        return receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));
    }

    @Override
    public Receipt update(UUID receiptId, Receipt receipt) {
        Receipt foundReceipt = receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));

        foundReceipt.setReceiptNumber(receipt.getReceiptNumber());
        foundReceipt.setPrice(receipt.getPrice());
        foundReceipt.setSupplier(receipt.getSupplier());
        foundReceipt.setReceiptDate(receipt.getReceiptDate());

        return receiptRepository.save(foundReceipt);
    }

    @Override
    public void delete(UUID receiptId) {
        receiptRepository.findById(receiptId).orElseThrow(() -> new NotFoundException("Receipt not found with ID: " + receiptId));
        receiptRepository.deleteById(receiptId);

    }
}
