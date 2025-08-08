package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.dtos.ResourceDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourceMapper {

    ResourceDto resourceToDto(Resource resource);

    Resource dtoToResource(ResourceDto resourceDto);
}
