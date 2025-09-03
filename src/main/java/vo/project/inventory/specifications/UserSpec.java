package vo.project.inventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vo.project.inventory.domain.User;

public class UserSpec {

    public static Specification<User> nameContains(String name) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(name)) return null;

            return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

}
