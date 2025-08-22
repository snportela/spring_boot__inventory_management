package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.ResourceDto;
import vo.project.inventory.query_filters.ResourceQueryFilter;
import vo.project.inventory.services.ResourceService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listResources(
            ResourceQueryFilter filter, Pageable pageable
    ) {
        Map<String, Object> response = resourceService.findAll(filter.toSpecification(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDto> getResource(@PathVariable("id") UUID resourceId) {
        ResourceDto foundResource = resourceService.findOne(resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(foundResource);
    }

    @PostMapping
    public ResponseEntity<ResourceDto> createResource(@Valid @RequestBody ResourceDto resourceDto) {
        ResourceDto savedResource = resourceService.save(resourceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDto> updateResource(
            @PathVariable("id") UUID resourceId,
            @Valid @RequestBody ResourceDto resourceDto
    ) {
        ResourceDto updatedResource = resourceService.update(resourceId, resourceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable("id") UUID resourceId) {
        resourceService.delete(resourceId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Resource with ID: " + resourceId);
    }
}
