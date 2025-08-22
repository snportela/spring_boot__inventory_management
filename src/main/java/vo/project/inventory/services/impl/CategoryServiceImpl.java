package vo.project.inventory.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vo.project.inventory.domain.Category;
import vo.project.inventory.dtos.CategoryDto;
import vo.project.inventory.exceptions.AlreadyExistsException;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.mappers.CategoryMapper;
import vo.project.inventory.repositories.CategoryRepository;
import vo.project.inventory.services.CategoryService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        if(categoryRepository.existsCategoryByName(categoryDto.name())) {
            throw new AlreadyExistsException("A Category with this name already exists.");
        }

        Category category = categoryRepository.save(categoryMapper.dtoToCategory(categoryDto));
        return categoryMapper.categoryToDto(category);
    }

    @Override
    public Map<String, Object> findAll(Specification<Category> spec, Pageable pageable) {

        Page<Category> categoryList = categoryRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("totalItems", categoryList.getTotalElements());
        response.put("totalPages", categoryList.getTotalPages());
        response.put("categories", categoryList.getContent().stream().map(categoryMapper::categoryToDto).collect(Collectors.toList()));
        response.put("currentPage", categoryList.getNumber());

        return response;
    }

    @Override
    public CategoryDto findOne(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        return categoryMapper.categoryToDto(category);
    }

    @Override
    public CategoryDto update(UUID categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));

        if(categoryRepository.existsCategoryByName(categoryDto.name())) {
            throw new AlreadyExistsException("A Category with this name already exists.");
        }

        existingCategory.setName(categoryDto.name());
        categoryRepository.save(existingCategory);

        return categoryMapper.categoryToDto(existingCategory);
    }

    @Override
    public void delete(UUID categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        categoryRepository.deleteById(categoryId);
    }
}
