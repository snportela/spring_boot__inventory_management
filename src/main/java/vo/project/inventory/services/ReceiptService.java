package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.dtos.ReceiptDto;

import java.util.Map;
import java.util.UUID;

public interface ReceiptService {

    ReceiptDto save(ReceiptDto receiptDto);

    Map<String, Object> findAll(Specification<Receipt> spec, Pageable pageable);

    ReceiptDto findOne(UUID receiptId);

    ReceiptDto update(UUID receiptId, ReceiptDto receiptDto);

    void delete(UUID receiptId);
}
