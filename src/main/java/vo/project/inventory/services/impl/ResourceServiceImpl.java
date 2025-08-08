package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.ResourceRepository;
import vo.project.inventory.services.ResourceService;

import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Page<Resource> findAll(Specification<Resource> spec, Pageable pageable) {
        return resourceRepository.findAll(spec, pageable);
    }

    @Override
    public Resource findOne(UUID resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));
    }

    @Override
    public Resource update(UUID resourceId, Resource resource) {
        Resource foundResource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

        foundResource.setArea(resource.getArea());
        foundResource.setCategory(resource.getCategory());
        foundResource.setName(resource.getName());
        foundResource.setDescription(resource.getDescription());
        foundResource.setManufactureYear(resource.getManufactureYear());
        foundResource.setSerialNumber(resource.getSerialNumber());
        foundResource.setRepairState(resource.getRepairState());
        foundResource.setResourceNumber(resource.getResourceNumber());
        foundResource.setStatus(resource.getStatus());
        foundResource.setObservation(resource.getObservation());
        foundResource.setUseTime(resource.getUseTime());

        return resourceRepository.save(foundResource);
    }

    @Override
    public void delete(UUID resourceId) {
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));
        resourceRepository.deleteById(resourceId);
    }
}
