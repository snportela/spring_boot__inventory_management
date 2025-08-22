package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Area;
import vo.project.inventory.dtos.AreaDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.AreaMapper;
import vo.project.inventory.repositories.AreaRepository;
import vo.project.inventory.services.AreaService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public AreaServiceImpl(AreaRepository areaRepository, AreaMapper areaMapper) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
    }

    @Override
    public AreaDto save(AreaDto areaDto) {
        if(areaRepository.existsAreaByName(areaDto.name())) {
            throw new AlreadyExistsException("An Area with this name already exists.");
        }

        Area area = areaRepository.save(areaMapper.dtoToArea(areaDto));
        return areaMapper.areaToDto(area);
    }

    @Override
    public Map<String, Object> findAll(Specification<Area> spec, Pageable pageable) {
        Page<Area> areaList = areaRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", areaList.getTotalElements());
        response.put("totalPages", areaList.getTotalPages());
        response.put("areas", areaList.getContent().stream().map(areaMapper::areaToDto).collect(Collectors.toList()));
        response.put("currentPage", areaList.getNumber());

        return response;
    }

    @Override
    public AreaDto findOne(UUID areaId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new NotFoundException("Area not found with id " + areaId));
        return areaMapper.areaToDto(area);
    }

    @Override
    public AreaDto update(UUID areaId, AreaDto areaDto) {
        Area existingArea = areaRepository.findById(areaId).orElseThrow(() -> new NotFoundException("Area not found with id" + areaId));

        if(areaRepository.existsAreaByName(areaDto.name())) {
            throw new AlreadyExistsException("An Area with this name already exists.");
        }

        existingArea.setName(areaDto.name());
        areaRepository.save(existingArea);

        return areaMapper.areaToDto(existingArea);
    }

    @Override
    public void delete(UUID areaId) {
        areaRepository.findById(areaId).orElseThrow(() -> new NotFoundException("Area not found with id" + areaId));
        areaRepository.deleteById(areaId);
    }
}
