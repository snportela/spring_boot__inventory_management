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
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.services.ReceiptService;

import java.math.BigDecimal;
import java.time.Instant;
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
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReceiptService receiptService;

    Receipt receiptA = new Receipt();
    Receipt receiptB = new Receipt();
    UUID receiptId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        receiptA = Receipt.builder()
                .receiptNumber("123")
                .price(new BigDecimal("10.00"))
                .supplier("abc")
                .receiptDate(Instant.parse("2010-10-27T10:30:00Z"))
                .build();

        receiptB = Receipt.builder()
                .receiptNumber("456")
                .price(new BigDecimal("20.00"))
                .supplier("def")
                .receiptDate(Instant.parse("2023-10-27T10:30:00Z"))
                .build();
    }

    @Test
    void ReceiptController_CreateReceipt_ReturnCreated() throws Exception {
        when(receiptService.save(any(Receipt.class))).thenReturn(receiptA);

        this.mockMvc.perform(post("/api/receipts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receiptA)))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplier", CoreMatchers.is(receiptA.getSupplier())));

        verify(receiptService,times(1)).save(any(Receipt.class));
    }

    @Test
    void ReceiptController_CreateInvalidReceipt_ReturnCorrectResponse() throws Exception {
        receiptA.setReceiptNumber("");
        when(receiptService.save(any(Receipt.class))).thenReturn(receiptA);

        this.mockMvc.perform(post("/api/receipts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receiptA)))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("{receiptNumber=Receipt Number must be between 2 and 100 characters long}")));
    }

    @Test
    void ReceiptController_ListReceipts_ReturnPaginatedItems() throws Exception {
        List<Receipt> list = new ArrayList<>();
        list.add(receiptA);
        list.add(receiptB);

        when(receiptService.findAll(Mockito.any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0,10), list.size()));

        this.mockMvc.perform(get("/api/receipts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems", CoreMatchers.is(list.size())));

        verify(receiptService, times(1)).findAll(Mockito.any(), any(Pageable.class));
    }

    @Test
    void ReceiptController_GetReceipt_ReturnReceipt() throws Exception {
        when(receiptService.findOne(receiptId)).thenReturn(receiptA);

        this.mockMvc.perform(get("/api/receipts/" + receiptId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receiptA)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiptNumber", CoreMatchers.is(receiptA.getReceiptNumber())));

        verify(receiptService, times(1)).findOne(receiptId);
    }

    @Test
    void ReceiptController_UpdateReceipt_ReturnReceipt() throws Exception {
        when(receiptService.update(receiptId, receiptA)).thenReturn(receiptA);

        this.mockMvc.perform(put("/api/receipts/" + receiptId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receiptA)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiptDate", CoreMatchers.is(receiptA.getReceiptDate().toString())));

        verify(receiptService, times(1)).update(receiptId, receiptA);
    }

    @Test
    void ReceiptController_DeleteReceipt_ReturnString() throws Exception {
        doNothing().when(receiptService).delete(receiptId);

        this.mockMvc.perform(delete("/api/receipts/" + receiptId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(receiptService, times(1)).delete(receiptId);
    }

}
