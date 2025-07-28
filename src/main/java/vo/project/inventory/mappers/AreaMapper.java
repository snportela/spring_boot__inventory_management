package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Area;
import vo.project.inventory.dtos.AreaDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AreaMapper {

    AreaDto areaToDto(Area area);

    Area dtoToArea(AreaDto areaDto);
}
