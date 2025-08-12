package vo.project.inventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vo.project.inventory.domain.Receipt;

public class ReceiptSpec {

    public static Specification<Receipt> supplierNameContains(String supplier) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(supplier)) return null;

            return builder.like(builder.lower(root.get("supplier")), "%" + supplier.toLowerCase() + "%");
        };
    }


}
