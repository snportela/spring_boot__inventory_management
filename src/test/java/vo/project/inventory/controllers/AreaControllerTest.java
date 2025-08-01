package vo.project.inventory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vo.project.inventory.InventoryApplication;
import vo.project.inventory.domain.Area;
import vo.project.inventory.services.AreaService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("SpellCheckingInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = InventoryApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class AreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    AreaService areaService;

    @Test
    public void AreaController_CreateArea_ReturnCreated() throws Exception {
        Area areaA = Area.builder().name("engenharia").build();

        when(areaService.save(any(Area.class))).thenReturn(areaA);

        this.mockMvc.perform(post("/api/areas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(areaA)))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(areaA.getName())));

        verify(areaService, times(1)).save(any(Area.class));
    }

    @Test
    public void AreaController_CreateInvalidArea_ReturnCorrectResponse() throws Exception {
        Area areaA = Area.builder().build();

        when(areaService.save(any(Area.class))).thenReturn(areaA);

        this.mockMvc.perform(post("/api/areas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(areaA)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("{name=Name is required}")));
    }

    @Test
    public void AreaController_ListAreas_ReturnPaginatedItems() throws Exception {
        List<Area> list = new ArrayList<>();
        list.add(Area.builder().name("engenharia").build());
        list.add(Area.builder().name("medicina").build());

        when(areaService.findAll(Mockito.any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0, 10), list.size()));

        this.mockMvc.perform(get("/api/areas")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "2")
                .param("sort", "name,asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems", CoreMatchers.is(list.size())));

        verify(areaService, times(1)).findAll(Mockito.any(), any(Pageable.class));
    }

    @Test
    public void AreaController_GetArea_ReturnAreaDto() throws Exception {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area areaA = Area.builder().areaId(areaId).name("engenharia").build();
        areaA.setAreaId(areaId);

        when(areaService.findOne(areaId)).thenReturn(areaA);

        this.mockMvc.perform(get("/api/areas/a1b2c3d4-e5f6-7890-1234-567890abcdef")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(areaA))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(areaA.getName())));

        verify(areaService, times(1)).findOne(areaId);
    }

    @Test
    public void AreaController_UpdateArea_ReturnAreaDto() throws Exception {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
        Area areaA = Area.builder().areaId(areaId).name("engenharia").build();
        areaA.setAreaId(areaId);

        when(areaService.update(areaId, areaA)).thenReturn(areaA);

        this.mockMvc.perform(put("/api/areas/a1b2c3d4-e5f6-7890-1234-567890abcdef")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(areaA))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(areaA.getName())));

        verify(areaService, times(1)).update(areaId, areaA);
    }

    @Test
    public void AreaController_DeleteArea_ReturnString() throws Exception {
        UUID areaId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");

        doNothing().when(areaService).delete(areaId);

        this.mockMvc.perform(delete("/api/areas/a1b2c3d4-e5f6-7890-1234-567890abcdef")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(areaService, times(1)).delete(areaId);
    }
}
