package vo.project.inventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;

import java.util.UUID;

public class ResourceSpec {

    public static Specification<Resource> nameContains(String name) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(name)) return null;

            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Resource> areaNameContains(String areaName) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(areaName)) return null;

            return builder.like(root.get("area").get("name"), "%" + areaName + "%");
        };
    }

    public static Specification<Resource> areaEquals(UUID areaId) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(areaId)) return null;

            return builder.equal(root.get("area").get("area_id"), areaId);
        };
    }

    public static Specification<Resource> categoryNameContains(String categoryName) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(categoryName)) return null;

            return builder.like(root.get("category").get("name"), "%" + categoryName + "%");
        };
    }

    public static Specification<Resource> categoryEquals(UUID categoryId) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(categoryId)) return null;

            return builder.equal(root.get("category_id"), categoryId);
        };
    }

    public static Specification<Resource> repairStateEquals(RepairState repairState) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(repairState)) return null;

            return builder.equal(root.get("repair_state"), repairState);
        };
    }

    public static Specification<Resource> statusEquals(Status status) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(status)) return null;

            return builder.equal(root.get("status"),  status);
        };
    }

    public static Specification<Resource> useTimeEquals(UseTime useTime) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(useTime)) return null;

            return builder.equal(root.get("use_time"), useTime);
        };
    }
}
