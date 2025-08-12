package vo.project.inventory.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Area;
import vo.project.inventory.domain.Category;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;
import vo.project.inventory.specifications.ResourceSpec;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Resource newResource = new Resource();
    Area area = new Area();
    Category category = new Category();

    @BeforeEach
    void setup() {
        area = Area.builder().name("engenharia").build();
        areaRepository.save(area);

        category = Category.builder().name("computador").build();
        categoryRepository.save(category);

        newResource = Resource.builder()
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
    void ResourceRepository_SaveResource_ReturnSavedResource() {
        resourceRepository.save(newResource);
        Optional<Resource> result = resourceRepository.findById(newResource.getResourceId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newResource);
    }

    @Test
    void ResourceRepository_FindAll_ReturnPaginatedItems() {
        resourceRepository.save(newResource);
        Pageable pageable = PageRequest.of(0, 2);
        Specification<Resource> spec = ResourceSpec
                .nameContains("PC")
                .and(ResourceSpec.statusEquals(Status.BEING_USED));

        Page<Resource> page = resourceRepository.findAll(spec, pageable);
        assertThat(page.getContent()).hasSize(1)
                .containsExactly(newResource);
    }

    @Test
    void ResourceRepository_FindById_ReturnResource() {
        resourceRepository.save(newResource);
        Optional<Resource> result = resourceRepository.findById(newResource.getResourceId());
        assertThat(result).isNotNull();
    }

    @Test
    void ResourceRepository_Update_ReturnResource() {
        resourceRepository.save(newResource);
        newResource.setName("updated");
        resourceRepository.save(newResource);
        Optional<Resource> result = resourceRepository.findById(newResource.getResourceId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(newResource.getName());
    }

    @Test
    void ResourceRepository_Delete_ReturnResourceIsEmpty() {
        resourceRepository.save(newResource);
        resourceRepository.deleteById(newResource.getResourceId());
        Optional<Resource> result = resourceRepository.findById(newResource.getResourceId());
        assertThat(result).isEmpty();
    }
}
