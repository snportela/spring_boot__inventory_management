package vo.project.inventory.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Category;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.CategoryRepository;
import vo.project.inventory.services.impl.CategoryServiceImpl;
import vo.project.inventory.specifications.CategorySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    UUID categoryId = UUID.randomUUID();

    @Test
    void CategoryService_CreateCategory_ReturnCategory() {
        Category category = Category.builder().name("livro").build();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category newCategory = categoryService.save(category);

        assertThat(newCategory).isNotNull();

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void CategoryService_FindAll_ReturnPaginatedItems() {
        List<Category> list = new ArrayList<>();
        list.add(Category.builder().name("livro").build());
        list.add(Category.builder().name("ferramenta").build());

        Pageable pageable = PageRequest.of(0, 10);
        Specification<Category> spec = CategorySpec.nameContains("");

        when(categoryRepository.findAll(spec, pageable))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0,10), list.size()));

        Page<Category> categories = categoryService.findAll(spec, pageable);

        assertEquals(2, categories.getTotalElements());
        assertNotNull(categories);
    }

    @Test
    void CategoryService_GetCategory_ReturnCategory() {
        Category category = Category.builder().categoryId(categoryId).name("livro").build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Category existingCategory = categoryService.findOne(categoryId);

        assertNotNull(existingCategory);
        assertEquals(categoryId, existingCategory.getCategoryId());
    }

    @Test
    void CategoryService_GetCategoryIsEmpty_ThrowsNotFound() {
        UUID categoryId = UUID.randomUUID();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> categoryService.findOne(categoryId));
    }

    @Test
    void CategoryService_UpdateCategory_ReturnCategory() {
        Category category = Category.builder().categoryId(categoryId).name("livro").build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        category.setName("computador");

        Category result = categoryService.update(categoryId, category);
        assertNotNull(result);
        assertEquals("computador", result.getName());
    }

    @Test
    void CategoryService_DeleteCategory_ReturnVoid() {
        Category category = Category.builder().categoryId(categoryId).name("livro").build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).deleteById(categoryId);

        assertAll(() -> categoryService.delete(categoryId));
    }
}
