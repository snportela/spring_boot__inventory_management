package vo.project.inventory.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Category;
import vo.project.inventory.specifications.CategorySpec;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void CategoryRepository_SaveCategory_ReturnSavedCategory() {
        Category newCategory = Category.builder().name("computador").build();
        categoryRepository.save(newCategory);
        Optional<Category> result = categoryRepository.findById(newCategory.getCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newCategory);
    }

    @Test
    void CategoryRepository_FindAll_ReturnPaginatedItems() {
        Category categoryA = Category.builder().name("computador").build();
        categoryRepository.save(categoryA);
        Category categoryB = Category.builder().name("livro").build();
        categoryRepository.save(categoryB);

        Pageable pageable = PageRequest.of(0, 2);
        Specification<Category> spec = CategorySpec.nameContains("comp");

        Page<Category> page = categoryRepository.findAll(spec, pageable);
        assertThat(page.getContent()).hasSize(1).containsExactly(categoryA);
    }

    @Test
    void CategoryRepository_FindById_ReturnCategory() {
        Category newCategory = Category.builder().name("computador").build();
        categoryRepository.save(newCategory);
        Optional<Category> result = categoryRepository.findById(newCategory.getCategoryId());
        assertThat(result).isNotNull();
    }

    @Test
    void CategoryRepository_Update_ReturnCategory() {
        Category categoryA = Category.builder().name("computador").build();
        categoryRepository.save(categoryA);
        categoryA.setName("updated");
        categoryRepository.save(categoryA);
        Optional<Category> result = categoryRepository.findById(categoryA.getCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(categoryA.getName());
    }

    @Test
    void CategoryRepository_Delete_ReturnCategoryIsEmpty() {
        Category categoryA = Category.builder().name("computador").build();
        categoryRepository.save(categoryA);
        categoryRepository.deleteById(categoryA.getCategoryId());
        Optional<Category> result = categoryRepository.findById(categoryA.getCategoryId());
        assertThat(result).isEmpty();
    }
}
