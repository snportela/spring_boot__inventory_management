package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.AppointmentDto;
import vo.project.inventory.services.AppointmentService;
import vo.project.inventory.specifications.AppointmentSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listAppointments(
            @RequestParam(required = false) String labName,
            @RequestParam(required = false) String requesterName,
            Pageable pageable
    ) {
        Map<String, Object> appointmentList = appointmentService
                .findAll(AppointmentSpec.labNameContains(labName).and(AppointmentSpec.requesterNameContains(requesterName)), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable("id") UUID appointmentId) {
        AppointmentDto appointment = appointmentService.findOne(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(appointment);
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        AppointmentDto appointment = appointmentService.save(appointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable("id") UUID appointmentId,
            @Valid @RequestBody AppointmentDto appointmentDto
    ) {
        AppointmentDto appointment = appointmentService.update(appointmentId, appointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable("id") UUID appointmentId) {
        appointmentService.delete(appointmentId);
        Map<String, String> response = new HashMap<>();

        response.put("message", "Loan with ID " + appointmentId + " was deleted successfully.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
