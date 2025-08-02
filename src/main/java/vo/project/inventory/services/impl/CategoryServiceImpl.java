package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Category;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.CategoryRepository;
import vo.project.inventory.services.CategoryService;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> findAll(Specification<Category> spec, Pageable pageable) {
        return categoryRepository.findAll(spec, pageable);
    }

    @Override
    public Category findOne(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
    }

    @Override
    public Category update(UUID categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(UUID categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        categoryRepository.deleteById(categoryId);
    }
}
