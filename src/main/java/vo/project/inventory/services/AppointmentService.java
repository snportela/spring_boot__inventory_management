package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Appointment;
import vo.project.inventory.dtos.AppointmentDto;

import java.util.Map;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDto save(AppointmentDto appointmentDto);

    Map<String, Object> findAll(Specification<Appointment> spec, Pageable pageable);

    AppointmentDto findOne(UUID appointmentId);

    AppointmentDto update(UUID appointmentId, AppointmentDto appointmentDto);

    void delete(UUID appointmentId);
}
