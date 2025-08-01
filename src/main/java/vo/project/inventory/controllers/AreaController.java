package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.domain.Area;
import vo.project.inventory.dtos.AreaDto;
import vo.project.inventory.mappers.AreaMapper;
import vo.project.inventory.services.AreaService;
import vo.project.inventory.specifications.AreaSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private final AreaService areaService;

    private final AreaMapper areaMapper;

    public AreaController(AreaService areaService, AreaMapper areaMapper) {
        this.areaService = areaService;
        this.areaMapper = areaMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listAreas(
            @RequestParam(required = false) String name,
            Pageable pageable) {
        Page<Area> areaList = areaService.findAll(AreaSpec.nameContains(name), pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("users", areaList.getContent().stream().map(areaMapper::areaToDto).collect(Collectors.toList()));
        response.put("currentPage", areaList.getNumber());
        response.put("totalItems", areaList.getTotalElements());
        response.put("totalPages", areaList.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDto> getArea(@PathVariable("id") UUID areaId) {
        Area foundArea = areaService.findOne(areaId);
        return ResponseEntity.status(HttpStatus.OK).body(areaMapper.areaToDto(foundArea));
    }

    @PostMapping
    public ResponseEntity<AreaDto> createArea(@Valid @RequestBody AreaDto areaDto) {
        Area savedArea = areaService.save(areaMapper.dtoToArea(areaDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(areaMapper.areaToDto(savedArea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDto> updateArea(
            @PathVariable("id") UUID areaId, @RequestBody AreaDto areaDto
    ) {
        Area updatedArea = areaService.update(areaId, areaMapper.dtoToArea(areaDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(areaMapper.areaToDto(updatedArea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArea(@PathVariable("id") UUID areaId) {
        areaService.delete(areaId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Area with ID: " + areaId);
    }
}
