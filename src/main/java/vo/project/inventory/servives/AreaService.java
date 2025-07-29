package vo.project.inventory.servives;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vo.project.inventory.domain.Area;

import java.util.UUID;

public interface AreaService {

    Area save(Area area);

    Page<Area> findAll(Pageable pageable);

    Area findOne(UUID areaId);

    Area update(UUID areaId, Area area);

    void delete(UUID areaId);
}
