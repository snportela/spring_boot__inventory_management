package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.LabDto;
import vo.project.inventory.services.LabService;
import vo.project.inventory.specifications.LabSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/labs")
public class LabController {

    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listLabs(
            @RequestParam(required = false) String name, Pageable pageable
    ) {

        Map<String, Object> labList = labService.findAll(LabSpec.nameContains(name), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(labList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabDto> getLab(@PathVariable("id") UUID labId) {
        LabDto lab = labService.findOne(labId);
        return ResponseEntity.status(HttpStatus.OK).body(lab);
    }

    @PostMapping
    public ResponseEntity<LabDto> createLab(@Valid @RequestBody LabDto labDto) {
        LabDto lab = labService.save(labDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lab);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabDto> updateLab(
            @PathVariable("id") UUID labId,
            @Valid @RequestBody LabDto labDto
    ) {
        LabDto lab = labService.update(labId, labDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lab);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLab(@PathVariable("id") UUID labId) {
        labService.delete(labId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Lab with ID " + labId + " was deleted successfully.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
