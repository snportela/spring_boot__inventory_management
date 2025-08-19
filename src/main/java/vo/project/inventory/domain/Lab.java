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
@SQLDelete(sql = "UPDATE labs SET deleted_at = NOW() WHERE lab_id = ?")
@SQLRestriction(value = "deleted_at IS NULL")
@Table(name= "labs")
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID labId;

    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
