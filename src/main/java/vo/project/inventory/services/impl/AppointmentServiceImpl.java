package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Appointment;
import vo.project.inventory.dtos.AppointmentDto;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.AppointmentMapper;
import vo.project.inventory.mappers.LabMapper;
import vo.project.inventory.repositories.AppointmentRepository;
import vo.project.inventory.services.AppointmentService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final LabMapper labMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, LabMapper labMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.labMapper = labMapper;
    }

    @Override
    public AppointmentDto save(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.save(appointmentMapper.dtoToAppointment(appointmentDto));
        return appointmentMapper.appointmentToDto(appointment);
    }

    @Override
    public Map<String, Object> findAll(Specification<Appointment> spec, Pageable pageable) {
        Page<Appointment> appointmentList = appointmentRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", appointmentList.getTotalElements());
        response.put("totalPages", appointmentList.getTotalPages());
        response.put("appointments", appointmentList.getContent().stream().map(appointmentMapper::appointmentToDto).collect(Collectors.toList()));
        response.put("currentPage", appointmentList.getNumber());

        return response;
    }

    @Override
    public AppointmentDto findOne(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NotFoundException("Could not find Appointment with ID: " + appointmentId));
        return appointmentMapper.appointmentToDto(appointment);
    }

    @Override
    public AppointmentDto update(UUID appointmentId, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NotFoundException("Could not find Appointment with ID: " + appointmentId));

        appointment.setLab(labMapper.dtoToLab(appointmentDto.lab()));
        appointment.setRequesterName(appointmentDto.requesterName());
        appointment.setRequesterId(appointmentDto.requesterId());
        appointment.setRequesterType(appointmentDto.requesterType());
        appointment.setRequesterEmail(appointmentDto.requesterEmail());
        appointment.setCheckinDate(appointmentDto.checkinDate());
        appointment.setCheckoutDate(appointmentDto.checkoutDate());
        appointment.setObservation(appointmentDto.observation());

        appointmentRepository.save(appointment);

        return appointmentMapper.appointmentToDto(appointment);
    }

    @Override
    public void delete(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new NotFoundException("Could not find Appointment with ID: " + appointmentId));
        appointmentRepository.delete(appointment);

    }
}
