package vo.project.inventory.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Area;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.AreaRepository;
import vo.project.inventory.services.impl.AreaServiceImpl;
import vo.project.inventory.specifications.AreaSpec;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    void AreaService_CreateArea_ReturnCreated() {
        Area area = Area.builder().name("engenharia").build();
        when(areaRepository.save(any(Area.class))).thenReturn(area);
        Area newArea = areaService.save(area);

        assertThat(newArea).isNotNull();

        verify(areaRepository).save(any(Area.class));
    }

    @Test
    void AreaService_FindAll_ReturnPaginatedItems() {
        List<Area> list = new ArrayList<>();
        list.add(Area.builder().name("engenharia").build());
        list.add(Area.builder().name("medicina").build());

        Pageable pageable = PageRequest.of(0, 10);
        Specification<Area> spec = AreaSpec.nameContains("");

        when(areaRepository.findAll(spec, pageable))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0, 10), list.size()));

        Page<Area> areas = areaService.findAll(spec, pageable);

        assertEquals(2, areas.getTotalElements());
        assertNotNull(areas);
    }

    @Test
    void AreaService_GetArea_ReturnArea() {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area area = Area.builder().areaId(areaId).name("engenharia").build();

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));
        Area existingArea = areaService.findOne(areaId);

        assertNotNull(existingArea);
        assertEquals(areaId, existingArea.getAreaId());
    }

    @Test
    void AreaService_GetAreaIsEmpty_ThrowsNotFound() {
        UUID areaId = UUID.randomUUID();

        when(areaRepository.findById(areaId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> areaService.findOne(areaId));
    }

    @Test
    void AreaService_UpdateArea_ReturnArea() {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area area = Area.builder().areaId(areaId).name("engenharia").build();

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));
        when(areaRepository.save(area)).thenReturn(area);

        area.setName("design");

        Area result = areaService.update(areaId, area);

        assertNotNull(result);
        assertEquals("design", result.getName());
    }

    @Test
    void AreaService_DeleteArea_ReturnVoid() {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area area = Area.builder().areaId(areaId).name("engenharia").build();

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));
        doNothing().when(areaRepository).deleteById(areaId);

        assertAll(() -> areaService.delete(areaId));
    }


}
