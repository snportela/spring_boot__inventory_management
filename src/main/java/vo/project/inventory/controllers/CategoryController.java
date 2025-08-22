package vo.project.inventory.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.project.inventory.dtos.CategoryDto;
import vo.project.inventory.services.CategoryService;
import vo.project.inventory.specifications.CategorySpec;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listCategories(
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        Map<String, Object> response = categoryService.findAll(CategorySpec.nameContains(name), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") UUID categoryId) {
        CategoryDto foundCategory = categoryService.findOne(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(foundCategory);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("id") UUID categoryId, @Valid @RequestBody CategoryDto categoryDto
    ) {
        CategoryDto updatedCategory = categoryService.update(categoryId, categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") UUID categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Category with ID: " + categoryId);
    }
}
