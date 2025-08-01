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
import org.springframework.test.context.TestPropertySource;
import vo.project.inventory.domain.Area;
import vo.project.inventory.specifications.AreaSpec;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(
        locations = "classpath:application-test.properties")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;

    @BeforeEach
    void init() {
        areaRepository.deleteAll();
    }

    @Test
    void AreaRepository_SaveArea_ReturnSavedArea() {
        Area newArea = Area.builder().name("engenharia").build();
        areaRepository.save(newArea);
        Optional<Area> result = areaRepository.findById(newArea.getAreaId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newArea);
    }

    @Test
    void AreaRepository_FindAll_ReturnPaginatedItems() {
        Area areaA = Area.builder().name("engenharia").build();
        areaRepository.save(areaA);
        Area areaB = Area.builder().name("medicina").build();
        areaRepository.save(areaB);

        Pageable pageable = PageRequest.of(0, 2);
        Specification<Area> spec = AreaSpec.nameContains("medic");

        Page<Area> page = areaRepository.findAll(spec, pageable);
        assertThat(page.getContent()).hasSize(1)
                .containsExactly(areaB);
    }

    @Test
    void AreaRepository_Update_ReturnArea() {
        Area areaA = Area.builder().name("engenharia").build();
        areaRepository.save(areaA);
        areaA.setName("updated");
        areaRepository.save(areaA);
        Optional<Area> result = areaRepository.findById(areaA.getAreaId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(areaA.getName());
    }

    @Test
    void AreaRepository_Delete_ReturnAreaIsEmpty() {
        Area areaA = Area.builder().name("engenharia").build();
        areaRepository.save(areaA);
        areaRepository.deleteById(areaA.getAreaId());
        Optional<Area> result = areaRepository.findById(areaA.getAreaId());
        assertThat(result).isEmpty();
    }

}
