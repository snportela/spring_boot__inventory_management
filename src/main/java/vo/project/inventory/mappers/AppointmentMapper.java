package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Appointment;
import vo.project.inventory.dtos.AppointmentDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    AppointmentDto appointmentToDto(Appointment appointment);

    Appointment dtoToAppointment(AppointmentDto appointmentDto);
}
