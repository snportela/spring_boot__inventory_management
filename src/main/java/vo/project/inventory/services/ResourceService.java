package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.ResourceDto;

import java.util.Map;
import java.util.UUID;

public interface ResourceService {

    ResourceDto save(ResourceDto resourceDto);

    Map<String, Object> findAll(Specification<Resource> spec, Pageable pageable);

    ResourceDto findOne(UUID resourceId);

    ResourceDto update(UUID resourceId, ResourceDto resourceDto);

    void delete(UUID resourceId);

}
