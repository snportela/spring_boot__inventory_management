package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.AreaDto;
import vo.project.inventory.services.AreaService;
import vo.project.inventory.specifications.AreaSpec;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listAreas(
            @RequestParam(required = false) String name,
            Pageable pageable) {
        Map<String, Object> areaList = areaService.findAll(AreaSpec.nameContains(name), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(areaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDto> getArea(@PathVariable("id") UUID areaId) {
        AreaDto foundArea = areaService.findOne(areaId);
        return ResponseEntity.status(HttpStatus.OK).body(foundArea);
    }

    @PostMapping
    public ResponseEntity<AreaDto> createArea(@Valid @RequestBody AreaDto areaDto) {
        AreaDto savedArea = areaService.save(areaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDto> updateArea(
            @PathVariable("id") UUID areaId, @Valid @RequestBody AreaDto areaDto
    ) {
        AreaDto updatedArea = areaService.update(areaId, areaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedArea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArea(@PathVariable("id") UUID areaId) {
        areaService.delete(areaId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Area with ID: " + areaId);
    }
}
