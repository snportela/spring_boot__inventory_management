package vo.project.inventory.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vo.project.inventory.domain.Appointment;

public class AppointmentSpec {

    public static Specification<Appointment> labNameContains(String labName) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(labName)) return null;

            return builder.like(builder.lower(root.get("lab").get("name")), "%" + labName.toLowerCase() + "%");
        };
    }

    public static Specification<Appointment> requesterNameContains(String requesterName) {
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(requesterName)) return null;

            return builder.like(builder.lower(root.get("requesterName")), "%" + requesterName.toLowerCase() + "%");
        };
    }


}
