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
import vo.project.inventory.domain.enums.RequesterType;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE appointments SET deleted_at = NOW() WHERE appointment_id = ?")
@SQLRestriction(value = "deleted_at IS NULL")
@Table(name= "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID appointmentId;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @Column(name = "requester_name")
    private String requesterName;

    @Column(name = "requester_id")
    private String requesterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "requester_type")
    private RequesterType requesterType;

    @Column(name = "requester_email")
    private String requesterEmail;

    @Column(name = "checkin_date")
    private Instant checkinDate;

    @Column(name = "checkout_date")
    private Instant checkoutDate;

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
