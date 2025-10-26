package vo.project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vo.project.inventory.domain.Receipt;

import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID>, JpaSpecificationExecutor<Receipt> {

    boolean existsReceiptByReceiptNumber(String receiptNumber);
}
