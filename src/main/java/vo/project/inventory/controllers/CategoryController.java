package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.domain.Category;
import vo.project.inventory.dtos.CategoryDto;
import vo.project.inventory.mappers.CategoryMapper;
import vo.project.inventory.services.CategoryService;
import vo.project.inventory.specifications.CategorySpec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listCategories(
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        Page<Category> categoryList = categoryService.findAll(CategorySpec.nameContains(name), pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("categories", categoryList.getContent().stream().map(categoryMapper::categoryToDto).collect(Collectors.toList()));
        response.put("currentPage", categoryList.getNumber());
        response.put("totalItems", categoryList.getTotalElements());
        response.put("totalPages", categoryList.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") UUID categoryId) {
        Category foundCategory = categoryService.findOne(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.categoryToDto(foundCategory));
    }

    @PostMapping
    public ResponseEntity<CategoryDto>  createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category savedCategory = categoryService.save(categoryMapper.dtoToCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToDto(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("id") UUID categoryId, @Valid @RequestBody CategoryDto categoryDto
    ) {
        Category updatedCategory = categoryService.update(categoryId, categoryMapper.dtoToCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") UUID categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Category with ID: " + categoryId);
    }
}
