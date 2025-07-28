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

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE areas SET deleted_at = NOW() WHERE area_id = ?")
@SQLRestriction(value = "deleted_at IS NULL")
@Table(name= "areas")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "area_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID areaId;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Instant deletedAt;
}
