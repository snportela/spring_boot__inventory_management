package vo.project.inventory.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "receipt_items")
@IdClass(ReceiptItemId.class)
public class ReceiptItem {

    @Id
    @Column(name = "receipt_id")
    private UUID receiptId;

    @Id
    @Column(name = "resource_id")
    private UUID resourceId;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    private String observation;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
