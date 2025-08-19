package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Lab;
import vo.project.inventory.dtos.LabDto;

import java.util.Map;
import java.util.UUID;

public interface LabService {

    LabDto save(LabDto labDto);

    Map<String, Object> findAll(Specification<Lab> spec, Pageable pageable);

    LabDto findOne(UUID labId);

    LabDto update(UUID labId, LabDto labDto);

    void delete(UUID labId);
}
