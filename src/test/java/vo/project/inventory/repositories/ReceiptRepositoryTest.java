package vo.project.inventory.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vo.project.inventory.domain.Receipt;
import vo.project.inventory.specifications.ReceiptSpec;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReceiptRepositoryTest {

    @Autowired
    private ReceiptRepository receiptRepository;

    Receipt receiptA = new Receipt();
    Receipt receiptB = new Receipt();

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
    void ReceiptRepository_SaveReceipt_ReturnSavedReceipt() {
        receiptRepository.save(receiptA);
        Optional<Receipt> result = receiptRepository.findById(receiptA.getReceiptId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(receiptA);
    }

    @Test
    void ReceiptRepository_FindAll_ReturnPaginatedItems() {
        receiptRepository.save(receiptA);
        receiptRepository.save(receiptB);

        Pageable pageable = PageRequest.of(0, 2);
        Specification<Receipt> spec = ReceiptSpec
                .supplierNameContains("abc");

        Page<Receipt> page = receiptRepository.findAll(spec, pageable);
        assertThat(page.getContent()).hasSize(1)
                .containsExactly(receiptA);
    }

    @Test
    void ReceiptRepository_FindById_ReturnReceipt() {
        receiptRepository.save(receiptA);
        Optional<Receipt> result = receiptRepository.findById(receiptA.getReceiptId());
        assertThat(result).isNotNull();
    }

    @Test
    void ReceiptRepositoryTest_Update_ReturnReceipt() {
        receiptRepository.save(receiptA);
        receiptA.setSupplier("updated");
        receiptRepository.save(receiptA);
        Optional<Receipt> result = receiptRepository.findById(receiptA.getReceiptId());
        assertThat(result).isPresent();
        assertThat(result.get().getSupplier()).isEqualTo(receiptA.getSupplier());
    }

    @Test
    void ReceiptRepository_Delete_ReturnReceiptIsEmpty() {
        receiptRepository.save(receiptA);
        receiptRepository.deleteById(receiptA.getReceiptId());
        Optional<Receipt> result = receiptRepository.findById(receiptA.getReceiptId());
        assertThat(result).isEmpty();
    }

}
