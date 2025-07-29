package vo.project.inventory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import vo.project.inventory.domain.Area;

import java.util.UUID;

public interface AreaRepository extends JpaRepository<Area, UUID> {

    @NonNull
    Page<Area> findAll(@NonNull Pageable pageable);
}
