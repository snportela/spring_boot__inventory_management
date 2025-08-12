package vo.project.inventory.services;

import org.junit.jupiter.api.BeforeEach;
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
import vo.project.inventory.domain.Category;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.AreaRepository;
import vo.project.inventory.repositories.CategoryRepository;
import vo.project.inventory.repositories.ResourceRepository;
import vo.project.inventory.services.impl.AreaServiceImpl;
import vo.project.inventory.services.impl.CategoryServiceImpl;
import vo.project.inventory.services.impl.ResourceServiceImpl;
import vo.project.inventory.specifications.ResourceSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private AreaServiceImpl areaService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    Resource resource = new Resource();
    Area area = new Area();
    Category category = new Category();
    UUID resourceId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        area = Area.builder().name("engenharia").build();
        areaService.save(area);

        category = Category.builder().name("computador").build();
        categoryService.save(category);

        resource = Resource.builder()
                .area(area)
                .category(category)
                .name("PC")
                .description("pc windows")
                .manufactureYear("2012")
                .serialNumber("N/A")
                .repairState(RepairState.OPERATIONAL)
                .resourceNumber("324343")
                .status(Status.BEING_USED)
                .observation("aaaa")
                .useTime(UseTime.LESS_THAN_ONE_YEAR)
                .build();
    }

    @Test
    void ResourceService_CreateResource_ReturnCreated() {
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        Resource newResource = resourceService.save(resource);

        assertThat(newResource).isNotNull();

        verify(resourceRepository).save(any(Resource.class));
    }

    @Test
    void ResourceService_FindAll_ReturnPaginatedItems() {
        List<Resource> list = new ArrayList<>();
        list.add(resource);

        Pageable pageable = PageRequest.of(0, 2);
        Specification<Resource> spec = ResourceSpec
                .nameContains("PC")
                .and(ResourceSpec.repairStateEquals(RepairState.OPERATIONAL));

        when(resourceRepository.findAll(spec, pageable))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0,10), list.size()));

        Page<Resource> resources = resourceService.findAll(spec, pageable);

        assertEquals(1, resources.getTotalElements());
        assertNotNull(resources);

    }

    @Test
    void ResourceService_GetResource_ReturnResource() {
        resource.setResourceId(resourceId);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        Resource existingResource = resourceService.findOne(resourceId);

        assertNotNull(existingResource);
        assertEquals(resourceId, existingResource.getResourceId());
    }

    @Test
    void ResourceService_GetResourceIsEmpty_ThrowsNotFound() {
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> resourceService.findOne(resourceId));
    }

    @Test
    void ResourceService_UpdateResource_ReturnResource() {
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(resource)).thenReturn(resource);

        resource.setName("updated");

        Resource result = resourceService.update(resourceId, resource);

        assertNotNull(result);
        assertEquals("updated", result.getName());
    }

    @Test
    void ResourceService_DeleteResource_ReturnVoid() {
        resource.setResourceId(resourceId);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        doNothing().when(resourceRepository).deleteById(resourceId);

        assertAll(()-> resourceService.delete(resourceId));
    }
}
