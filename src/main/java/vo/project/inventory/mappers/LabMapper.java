package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Lab;
import vo.project.inventory.dtos.LabDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LabMapper {

    LabDto labToDto(Lab lab);

    Lab dtoToLab(LabDto labDto);
}
