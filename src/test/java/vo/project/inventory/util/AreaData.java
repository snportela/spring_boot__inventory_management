package vo.project.inventory.util;

import vo.project.inventory.domain.Area;

public final class AreaData {

    public static Area createTestAreaEntityA() {
        return
                Area.builder()
                        .name("engenharia")
                        .build();
    }

    public static Area createTestAreaEntityB() {
        return
                Area.builder()
                        .name("medicina")
                        .build();
    }
}
