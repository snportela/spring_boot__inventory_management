package vo.project.inventory.services;

import org.junit.jupiter.api.BeforeEach;
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
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.exceptions.NotFoundException;
import vo.project.inventory.repositories.ReceiptRepository;
import vo.project.inventory.services.impl.ReceiptServiceImpl;
import vo.project.inventory.specifications.ReceiptSpec;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

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
    void ReceiptService_CreateReceipt_ReturnCreated() {
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receiptA);

        Receipt newReceipt = receiptService.save(receiptA);

        assertThat(newReceipt).isNotNull();

        verify(receiptRepository).save(any(Receipt.class));
    }

    @Test
    void ReceiptService_FindAll_ReturnPaginatedItems() {
        List<Receipt> list = new ArrayList<>();
        list.add(receiptB);

        Pageable pageable = PageRequest.of(0, 2);
        Specification<Receipt> spec = ReceiptSpec
                .supplierNameContains("def");

        when(receiptRepository.findAll(spec, pageable))
                .thenReturn(new PageImpl<>(list, PageRequest.of(0, 10), list.size()));

        Page<Receipt> receipts = receiptService.findAll(spec, pageable);

        assertEquals(1, receipts.getTotalElements());
        assertNotNull(receipts);
    }

    @Test
    void ReceiptService_GetReceipt_ReturnReceipt() {
        receiptA.setReceiptId(receiptId);

        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(receiptA));
        Receipt existingReceipt = receiptService.findOne(receiptId);

        assertNotNull(existingReceipt);
        assertEquals(receiptA.getReceiptId(), existingReceipt.getReceiptId());
    }

    @Test
    void ReceiptService_GetReceiptIsEmpty_ThrowsNotFound() {
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> receiptService.findOne(receiptId));
    }

    @Test
    void ReceiptService_UpdateReceipt_ReturnReceipt() {
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(receiptA));
        when(receiptRepository.save(receiptA)).thenReturn(receiptA);

        receiptA.setSupplier("updated");

        Receipt result = receiptService.update(receiptId, receiptA);

        assertNotNull(result);
        assertEquals("updated", result.getSupplier());
    }

    @Test
    void ReceiptService_DeleteReceipt_ReturnVoid() {
        receiptA.setReceiptId(receiptId);

        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(receiptA));
        doNothing().when(receiptRepository).deleteById(receiptId);

        assertAll(() -> receiptService.delete(receiptId));
    }



}
