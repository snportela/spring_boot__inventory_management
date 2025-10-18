package vo.project.inventory.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE receipts SET deleted_at = NOW() WHERE receipt_id = ?")
@SQLRestriction(value = "deleted_at IS NULL")
@Table(name= "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "receipt_id")
    private UUID receiptId;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "access_key")
    private String accessKey;

    @Column(scale = 10, precision = 2)
    private BigDecimal price;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "receipt_date")
    private Instant receiptDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
