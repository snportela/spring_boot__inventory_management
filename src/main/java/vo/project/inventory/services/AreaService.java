package vo.project.inventory.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Area;

import java.util.UUID;

public interface AreaService {

    Area save(Area area);

    Page<Area> findAll(Specification<Area> spec, Pageable pageable);

    Area findOne(UUID areaId);

    Area update(UUID areaId, Area area);

    void delete(UUID areaId);
}
