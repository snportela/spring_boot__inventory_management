package vo.project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vo.project.inventory.domain.Lab;

import java.util.UUID;

public interface LabRepository extends JpaRepository<Lab, UUID>, JpaSpecificationExecutor<Lab> {

    boolean existsByName(String name);
}
