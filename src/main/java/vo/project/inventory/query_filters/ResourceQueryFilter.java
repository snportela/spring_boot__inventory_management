package vo.project.inventory.query_filters;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;

import java.util.UUID;

import static vo.project.inventory.specifications.ResourceSpec.*;

@Data
public class ResourceQueryFilter {

    private String name;

    private UUID areaId;

    private UUID categoryId;

    private RepairState repairState;

    private Status status;

    private UseTime useTime;

    public Specification<Resource> toSpecification() {
        return nameContains(name)
                .and(areaEquals(areaId))
                .and(categoryEquals(categoryId))
                .and(repairStateEquals(repairState))
                .and(statusEquals(status))
                .and(useTimeEquals(useTime));
    }
}
