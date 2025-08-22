package vo.project.inventory.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Category;
import vo.project.inventory.dtos.CategoryDto;

import java.util.Map;
import java.util.UUID;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);

    Map<String, Object> findAll(Specification<Category> spec, Pageable pageable);

    CategoryDto findOne(UUID categoryId);

    CategoryDto update(UUID categoryId, CategoryDto categoryDto);

    void delete(UUID categoryId);
}
