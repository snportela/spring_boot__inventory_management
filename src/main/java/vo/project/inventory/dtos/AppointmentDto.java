package vo.project.inventory.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vo.project.inventory.domain.enums.RequesterType;

import java.time.Instant;
import java.util.UUID;

public record AppointmentDto(

        UUID appointmentId,

        @NotNull(message = "Lab is required")
        LabDto lab,

        @NotNull(message = "Requester name is required")
        @Size(min = 2, max = 100, message = "Requester name must be between 2 and 100 characters long")
        String requesterName,

        @NotNull(message = "Requester ID is required")
        String requesterId,

        @NotNull(message = "Requester Type is required")
        RequesterType requesterType,

        @NotNull(message = "Requester Email is required")
        @Email(message = "Requester Email must be in a valid email format.")
        String requesterEmail,

        @NotNull(message = "Checkin Date is required")
        Instant checkinDate,

        @NotNull(message = "Checkin Date is required")
        Instant checkoutDate,

        String observation
) {
}
