package vo.project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vo.project.inventory.domain.Area;

import java.util.UUID;

public interface AreaRepository extends JpaRepository<Area, UUID> {
}
