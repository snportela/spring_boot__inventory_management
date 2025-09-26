package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.ResourceDto;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.AreaMapper;
import vo.project.inventory.mappers.CategoryMapper;
import vo.project.inventory.mappers.ReceiptMapper;
import vo.project.inventory.mappers.ResourceMapper;
import vo.project.inventory.repositories.ResourceRepository;
import vo.project.inventory.services.ResourceService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final AreaMapper areaMapper;
    private final CategoryMapper categoryMapper;
    private final ReceiptMapper receiptMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper, AreaMapper areaMapper, CategoryMapper categoryMapper, ReceiptMapper receiptMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
        this.areaMapper = areaMapper;
        this.categoryMapper = categoryMapper;
        this.receiptMapper = receiptMapper;
    }

    @Override
    public ResourceDto save(ResourceDto resourceDto) {
        Resource resource = resourceRepository.save(resourceMapper.dtoToResource(resourceDto));
        return resourceMapper.resourceToDto(resource);
    }

    @Override
    public Map<String, Object> findAll(Specification<Resource> spec, Pageable pageable) {
        Page<Resource> resourceList = resourceRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", resourceList.getTotalElements());
        response.put("totalPages", resourceList.getTotalPages());
        response.put("resources", resourceList.getContent().stream().map(resourceMapper::resourceToDto).collect(Collectors.toList()));
        response.put("currentPage", resourceList.getNumber());

        return response;
    }

    @Override
    public ResourceDto findOne(UUID resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));
        return resourceMapper.resourceToDto(resource);
    }

    @Override
    public ResourceDto update(UUID resourceId, ResourceDto resourceDto) {
        Resource foundResource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

        foundResource.setArea(areaMapper.dtoToArea(resourceDto.area()));
        foundResource.setCategory(categoryMapper.dtoToCategory(resourceDto.category()));
        foundResource.setReceipt(receiptMapper.dtoToReceipt(resourceDto.receipt()));
        foundResource.setPrice(resourceDto.price());
        foundResource.setName(resourceDto.name());
        foundResource.setDescription(resourceDto.description());
        foundResource.setManufactureYear(resourceDto.manufactureYear());
        foundResource.setSerialNumber(resourceDto.serialNumber());
        foundResource.setRepairState(resourceDto.repairState());
        foundResource.setResourceNumber(resourceDto.resourceNumber());
        foundResource.setStatus(resourceDto.status());
        foundResource.setObservation(resourceDto.observation());
        foundResource.setUseTime(resourceDto.useTime());

        resourceRepository.save(foundResource);

        return resourceMapper.resourceToDto(foundResource);
    }

    @Override
    public void delete(UUID resourceId) {
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));
        resourceRepository.deleteById(resourceId);
    }
}
