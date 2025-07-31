package vo.project.inventory.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import vo.project.inventory.domain.Area;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.AreaRepository;
import vo.project.inventory.services.impl.AreaServiceImpl;
import vo.project.inventory.util.AreaData;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AreaServiceTest {

    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private AreaServiceImpl areaService;

    @Test
    void testThatAreaCanBeCreated() {
        Area areaA = AreaData.createTestAreaEntityA();
        when(areaRepository.save(any(Area.class))).thenReturn(areaA);
        Area newArea = areaService.save(areaA);

        assertEquals("engenharia", newArea.getName());
        verify(areaRepository).save(any(Area.class));
    }

    @Test
    void testThatMultipleAreasCanBeCreatedAndRecalled() {
        List<Area> list = new ArrayList<>();
        list.add(AreaData.createTestAreaEntityA());
        list.add(AreaData.createTestAreaEntityB());

        Pageable pageable = PageRequest.of(0, 10);

        when(areaRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0, 10), list.size()));

        Page<Area> areas = areaService.findAll(pageable);

        assertEquals(2, areas.getTotalElements());
        assertNotNull(areas);
    }

    @Test
    void testThatAreaCanBeRecalledById() {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area areaA = AreaData.createTestAreaEntityA();
        areaA.setAreaId(areaId);

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(areaA));
        Area existingArea = areaService.findOne(areaId);

        assertNotNull(existingArea);
        assertEquals(areaId, existingArea.getAreaId());
    }

    @Test
    void testThatExceptionShouldBeThrownWhenAreaNotFound() {
        UUID areaId = UUID.randomUUID();

        when(areaRepository.findById(areaId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> areaService.findOne(areaId));
    }

    @Test
    void testThatAreaCanBeUpdated() {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area areaA = AreaData.createTestAreaEntityA();
        areaA.setAreaId(areaId);

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(areaA));
        when(areaRepository.save(areaA)).thenReturn(areaA);

        areaA.setName("design");

        Area result = areaService.update(areaId, areaA);

        assertNotNull(result);
        assertEquals("design", result.getName());
    }

    @Test
    void testThatAreaCanBeDeleted() {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area areaA = AreaData.createTestAreaEntityA();

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(areaA));
        doNothing().when(areaRepository).deleteById(areaId);

        assertAll(() -> areaService.delete(areaId));
    }


}
