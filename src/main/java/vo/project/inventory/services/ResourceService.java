package vo.project.inventory.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Resource;

import java.util.UUID;

public interface ResourceService {

    Resource save(Resource resource);

    Page<Resource> findAll(Specification<Resource> spec, Pageable pageable);

    Resource findOne(UUID resourceId);

    Resource update(UUID resourceId, Resource resource);

    void delete(UUID resourceId);

}
