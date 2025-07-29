package vo.project.inventory.servives.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Area;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.AreaRepository;
import vo.project.inventory.servives.AreaService;

import java.util.UUID;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public Area save(Area area) {
        return areaRepository.save(area);
    }

    @Override
    public Page<Area> findAll(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    @Override
    public Area findOne(UUID areaId) {
        return areaRepository.findById(areaId).orElseThrow(() -> new NotFoundException("Area not found with id " + areaId));
    }

    @Override
    public Area update(UUID areaId, Area area) {
        Area existingArea = areaRepository.findById(areaId).orElseThrow(() -> new NotFoundException("Area not found with id" + areaId));
        existingArea.setName(area.getName());
        return areaRepository.save(existingArea);
    }

    @Override
    public void delete(UUID areaId) {
        areaRepository.findById(areaId).orElseThrow(() -> new NotFoundException("Area not found with id" + areaId));
        areaRepository.deleteById(areaId);
    }
}
