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
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE resources SET deleted_at = NOW() WHERE resource_id = ?")
@SQLRestriction(value = "deleted_at IS NULL")
@Table(name= "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resource_id")
    private UUID resourceId;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String description;

    @Column(name = "manufacture_year")
    private String manufactureYear;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "repair_state")
    private RepairState repairState;

    @Column(name = "resource_number")
    private String resourceNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String observation;

    @Enumerated(EnumType.STRING)
    private UseTime useTime;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}
