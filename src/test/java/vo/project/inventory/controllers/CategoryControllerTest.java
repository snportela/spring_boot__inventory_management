package vo.project.inventory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vo.project.inventory.InventoryApplication;
import vo.project.inventory.domain.Category;
import vo.project.inventory.services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = InventoryApplication.class)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    CategoryService categoryService;

    @Test
    public void CategoryController_CreateCategory_ReturnCreated() throws Exception {
        Category categoryA = Category.builder().name("livro").build();

        when(categoryService.save(any(Category.class))).thenReturn(categoryA);

        this.mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryA))
        )
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(categoryA.getName())));

        verify(categoryService, times(1)).save(any(Category.class));
    }

    @Test
    public void CategoryController_CreateInvalidCategory_ReturnCorrectResponse() throws Exception {
        Category categoryA = Category.builder().build();

        when(categoryService.save(any(Category.class))).thenReturn(categoryA);

        this.mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryA))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("{name=Category name is required}")));
    }

    @Test
    public void CategoryController_ListCategories_ReturnPaginatedItems() throws Exception {
        List<Category> list = new ArrayList<>();
        list.add(Category.builder().name("livro").build());
        list.add(Category.builder().name("computador").build());

        when(categoryService.findAll(Mockito.any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0,10), list.size()));

        this.mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "2")
                .param("sort", "name,asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems", CoreMatchers.is(list.size())));

        verify(categoryService, times(1)).findAll(Mockito.any(), any(Pageable.class));
    }

    @Test
    public void CategoryController_GetCategory_ReturnCategory() throws Exception {
        UUID categoryId = UUID.fromString("3d77b4cc-e414-4286-8cc3-cc9716255e6d");
        Category categoryA = Category.builder().categoryId(categoryId).name("livro").build();

        when(categoryService.findOne(categoryId)).thenReturn(categoryA);

        this.mockMvc.perform(get("/api/categories/3d77b4cc-e414-4286-8cc3-cc9716255e6d")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(categoryA.getName())));

    }

    @Test
    public void CategoryController_UpdateCategory_ReturnCategory() throws Exception {
        UUID categoryId = UUID.fromString("3d77b4cc-e414-4286-8cc3-cc9716255e6d");
        Category categoryA = Category.builder().categoryId(categoryId).name("livro").build();

        when(categoryService.update(categoryId, categoryA)).thenReturn(categoryA);

        this.mockMvc.perform(put("/api/categories/3d77b4cc-e414-4286-8cc3-cc9716255e6d")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryA))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(categoryA.getName())));

        verify(categoryService, times(1)).update(categoryId, categoryA);
    }

    @Test
    public void CategoryController_DeleteCategory_ReturnString() throws Exception {
        UUID categoryId = UUID.fromString("3d77b4cc-e414-4286-8cc3-cc9716255e6d");

        doNothing().when(categoryService).delete(categoryId);

        this.mockMvc.perform(delete("/api/categories/3d77b4cc-e414-4286-8cc3-cc9716255e6d")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(categoryService, times(1)).delete(categoryId);
    }
}
