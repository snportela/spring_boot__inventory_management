package vo.project.inventory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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
import vo.project.inventory.domain.Area;
import vo.project.inventory.domain.Category;
import vo.project.inventory.domain.Resource;
import vo.project.inventory.domain.enums.RepairState;
import vo.project.inventory.domain.enums.Status;
import vo.project.inventory.domain.enums.UseTime;
import vo.project.inventory.services.AreaService;
import vo.project.inventory.services.CategoryService;
import vo.project.inventory.services.ResourceService;

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
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AreaService areaService;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    ResourceService resourceService;

    Resource resource = new Resource();
    Area area = new Area();
    Category category = new Category();
    UUID resourceId = UUID.randomUUID();


    @BeforeEach
    void setup() {
        area = Area.builder().name("engenharia").build();
        areaService.save(area);

        category = Category.builder().name("computador").build();
        categoryService.save(category);

        resource = Resource.builder()
                .area(area)
                .category(category)
                .name("PC")
                .description("pc windows")
                .manufactureYear("n/a")
                .serialNumber("N/A")
                .repairState(RepairState.OPERATIONAL)
                .resourceNumber("324343")
                .status(Status.BEING_USED)
                .observation("aaaa")
                .useTime(UseTime.LESS_THAN_ONE_YEAR)
                .build();
    }

    @Test
    void ResourceController_CreateResource_ReturnCreated() throws Exception {

        when(resourceService.save(any(Resource.class))).thenReturn(resource);

        this.mockMvc.perform(post("/api/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource))
        )
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(resource.getName())));

        verify(resourceService,times(1)).save(any(Resource.class));
    }

    @Test
    void ResourceController_CreateInvalidResource_ReturnCorrectResponse() throws Exception {
        resource.setName("");
        when(resourceService.save(any(Resource.class))).thenReturn(resource);

        this.mockMvc.perform(post("/api/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("{name=Resource name must be between 2 and 100 characters long}")));
    }

    @Test
    void ResourceController_ListResources_ReturnPaginatedItems() throws Exception {
        List<Resource> list = new ArrayList<>();
        list.add(resource);

        when(resourceService.findAll(Mockito.any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0,10), list.size()));

        this.mockMvc.perform(get("/api/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems", CoreMatchers.is(list.size())));

        verify(resourceService, times(1)).findAll(Mockito.any(), any(Pageable.class));

    }

    @Test
    void ResourceController_GetResource_ReturnResource() throws Exception {
        when(resourceService.findOne(resourceId)).thenReturn(resource);

        this.mockMvc.perform(get("/api/resources/" + resourceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(resource.getName())));

        verify(resourceService,times(1)).findOne(resourceId);
    }

    @Test
    void ResourceController_UpdateResource_ReturnResource() throws Exception {
        when(resourceService.update(resourceId, resource)).thenReturn(resource);

        this.mockMvc.perform(put("/api/resources/" + resourceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(resource.getName())));

        verify(resourceService, times(1)).update(resourceId, resource);
    }

    @Test
    void ResourceController_DeleteResource_ReturnString() throws Exception {
        doNothing().when(resourceService).delete(resourceId);

        this.mockMvc.perform(delete("/api/resources/" + resourceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(resourceService, times(1)).delete(resourceId);
    }

}
