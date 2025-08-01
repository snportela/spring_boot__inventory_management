package vo.project.inventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vo.project.inventory.domain.Area;

public class AreaSpec {

    public static Specification<Area> nameContains(String name) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(name)) return null;

            return builder.like(root.get("name"), "%" + name + "%");
        };
    }
}
