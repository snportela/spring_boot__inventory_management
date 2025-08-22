package vo.project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vo.project.inventory.domain.Area;

import java.util.UUID;

public interface AreaRepository extends JpaRepository<Area, UUID>, JpaSpecificationExecutor<Area> {

    boolean existsAreaByName(String name);
}
