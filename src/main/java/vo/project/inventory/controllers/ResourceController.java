package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.ResourceDto;
import vo.project.inventory.mappers.ResourceMapper;
import vo.project.inventory.query_filters.ResourceQueryFilter;
import vo.project.inventory.services.ResourceService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    private final ResourceMapper resourceMapper;

    public ResourceController(ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceService = resourceService;
        this.resourceMapper = resourceMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listResources(
            ResourceQueryFilter filter, Pageable pageable
    ) {
        Page<Resource> resourceList = resourceService.findAll(filter.toSpecification(), pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("resources", resourceList.getContent().stream().map(resourceMapper::resourceToDto).collect(Collectors.toList()));
        response.put("currentPage", resourceList.getNumber());
        response.put("totalItems", resourceList.getTotalElements());
        response.put("totalPages", resourceList.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDto> getResource(@PathVariable("id") UUID resourceId) {
        Resource foundResource = resourceService.findOne(resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(resourceMapper.resourceToDto(foundResource));
    }

    @PostMapping
    public ResponseEntity<ResourceDto> createResource(@Valid @RequestBody ResourceDto resourceDto) {
        Resource savedResource = resourceService.save(resourceMapper.dtoToResource(resourceDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceMapper.resourceToDto(savedResource));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDto> updateResource(
            @PathVariable("id") UUID resourceId,
            @Valid @RequestBody ResourceDto resourceDto
    ) {
        Resource updatedResource = resourceService.update(resourceId, resourceMapper.dtoToResource(resourceDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceMapper.resourceToDto(updatedResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable("id") UUID resourceId) {
        resourceService.delete(resourceId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Resource with ID: " + resourceId);
    }
}
