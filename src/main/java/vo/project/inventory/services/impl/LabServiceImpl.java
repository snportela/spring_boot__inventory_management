package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Lab;
import vo.project.inventory.dtos.LabDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.LabMapper;
import vo.project.inventory.repositories.LabRepository;
import vo.project.inventory.services.LabService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LabServiceImpl implements LabService {

    private final LabRepository labRepository;
    private final LabMapper labMapper;

    public LabServiceImpl(LabRepository labRepository, LabMapper labMapper) {
        this.labRepository = labRepository;
        this.labMapper = labMapper;
    }

    @Override
    public LabDto save(LabDto labDto) {
        if(labRepository.existsByName(labDto.name())) {
            throw new AlreadyExistsException("A Lab with this name already exists.");
        }

        Lab lab = labRepository.save(labMapper.dtoToLab(labDto));
        return labMapper.labToDto(lab);
    }

    @Override
    public Map<String, Object> findAll(Specification<Lab> spec, Pageable pageable) {
        Page<Lab> labList = labRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", labList.getTotalElements());
        response.put("totalPages", labList.getTotalPages());
        response.put("labs", labList.getContent().stream().map(labMapper::labToDto).collect(Collectors.toList()));
        response.put("currentPage", labList.getNumber());

        return response;
    }

    @Override
    public LabDto findOne(UUID labId) {
        Lab lab = labRepository.findById(labId).orElseThrow(() -> new NotFoundException("Could not find Lab with ID: " + labId));
        return labMapper.labToDto(lab);
    }

    @Override
    public LabDto update(UUID labId, LabDto labDto) {
        Lab lab = labRepository.findById(labId).orElseThrow(() -> new NotFoundException("Could not find Lab with ID: " + labId));

        if(labRepository.existsByName(labDto.name())) {
            throw new AlreadyExistsException("A Lab with this name already exists.");
        }

        lab.setName(labDto.name());
        labRepository.save(lab);

        return labMapper.labToDto(lab);
    }

    @Override
    public void delete(UUID labId) {
        Lab lab =  labRepository.findById(labId).orElseThrow(() -> new NotFoundException("Could not find Lab with ID: " + labId));
        labRepository.delete(lab);
    }
}
