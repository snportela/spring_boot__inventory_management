package vo.project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vo.project.inventory.domain.ReceiptItem;
import vo.project.inventory.domain.ReceiptItemId;

import java.util.List;
import java.util.UUID;


public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, ReceiptItemId> {

    @Query("SELECT ri, r, res FROM ReceiptItem ri " +
            "JOIN Receipt r ON ri.receiptId = r.receiptId " +
            "JOIN Resource res ON ri.resourceId = res.resourceId " +
            "WHERE ri.receiptId = :receiptId AND ri.deletedAt IS NULL")
    List<Object[]> findByReceiptId(UUID receiptId);

    @Query("SELECT ri, r, res FROM ReceiptItem ri " +
            "JOIN Receipt r ON ri.receiptId = r.receiptId " +
            "JOIN Resource res ON ri.resourceId = res.resourceId " +
            "WHERE ri.resourceId = :resourceId AND ri.deletedAt IS NULL")
    List<Object[]> findByResourceId(UUID resourceId);

    @Modifying
    @Query("UPDATE ReceiptItem ri SET ri.deletedAt = CURRENT_TIMESTAMP WHERE ri.receiptId = :receiptId AND ri.deletedAt IS NULL")
    void deleteByReceiptId(UUID receiptId);

    @Modifying
    @Query("UPDATE ReceiptItem ri SET ri.deletedAt = CURRENT_TIMESTAMP WHERE ri.receiptId = :receiptId AND ri.resourceId = :resourceId AND ri.deletedAt IS NULL")
    void deleteByReceiptIdAndResourceId(UUID receiptId, UUID resourceId);

    @Query("SELECT CASE WHEN COUNT(ri) > 0 THEN true ELSE false END FROM ReceiptItem ri WHERE ri.receiptId = :receiptId AND ri.resourceId = :resourceId AND ri.deletedAt IS NULL")
    boolean existsByReceiptIdAndResourceId(UUID receiptId, UUID resourceId);

}
