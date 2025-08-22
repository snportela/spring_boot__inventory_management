package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Area;
import vo.project.inventory.dtos.AreaDto;

import java.util.Map;
import java.util.UUID;

public interface AreaService {

    AreaDto save(AreaDto areaDto);

    Map<String, Object> findAll(Specification<Area> spec, Pageable pageable);

    AreaDto findOne(UUID areaId);

    AreaDto update(UUID areaId, AreaDto areaDto);

    void delete(UUID areaId);
}
