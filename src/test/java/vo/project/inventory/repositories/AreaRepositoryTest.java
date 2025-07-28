package vo.project.inventory.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vo.project.inventory.domain.Area;
import vo.project.inventory.util.AreaData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;

    @BeforeEach
    void init() {
        areaRepository.deleteAll();
    }

    @Test
    void testThatAreaCanBeCreatedAndRecalled() {
        Area newArea = AreaData.createTestAreaEntityA();
        areaRepository.save(newArea);
        Optional<Area> result = areaRepository.findById(newArea.getAreaId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newArea);
    }

    @Test
    void testThatMultipleAreasCanBeCreatedAndRecalled() {
        Area areaA = AreaData.createTestAreaEntityA();
        areaRepository.save(areaA);
        Area areaB = AreaData.createTestAreaEntityB();
        areaRepository.save(areaB);

        Iterable<Area> result = areaRepository.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactly(areaA, areaB);
    }

    @Test
    void testThatAreaCanBeUpdated() {
        Area areaA = AreaData.createTestAreaEntityA();
        areaRepository.save(areaA);
        areaA.setName("updated");
        areaRepository.save(areaA);
        Optional<Area> result = areaRepository.findById(areaA.getAreaId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(areaA.getName());
    }

    @Test
    void testThatAreaCanBeDeleted() {
        Area areaA = AreaData.createTestAreaEntityA();
        areaRepository.save(areaA);
        areaRepository.deleteById(areaA.getAreaId());
        Optional<Area> result = areaRepository.findById(areaA.getAreaId());
        assertThat(result).isEmpty();
    }

}
