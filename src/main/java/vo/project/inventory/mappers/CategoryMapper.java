package vo.project.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vo.project.inventory.domain.Category;
import vo.project.inventory.dtos.CategoryDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto categoryToDto(Category category);

    Category dtoToCategory(CategoryDto categoryDto);
}
