package vo.project.inventory.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Category;

import java.util.UUID;

public interface CategoryService {

    Category save(Category category);

    Page<Category> findAll(Specification<Category> spec, Pageable pageable);

    Category findOne(UUID categoryId);

    Category update(UUID categoryId, Category category);

    void delete(UUID categoryId);
}
