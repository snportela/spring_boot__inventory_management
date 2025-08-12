package vo.project.inventory.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Receipt;

import java.util.UUID;

public interface ReceiptService {

    Receipt save(Receipt receipt);

    Page<Receipt> findAll(Specification<Receipt> spec, Pageable pageable);

    Receipt findOne(UUID receiptId);

    Receipt update(UUID receiptId, Receipt receipt);

    void delete(UUID receiptId);
}
