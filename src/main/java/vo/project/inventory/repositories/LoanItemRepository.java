package vo.project.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vo.project.inventory.domain.LoanItem;
import vo.project.inventory.domain.LoanItemId;

import java.util.List;
import java.util.UUID;

public interface LoanItemRepository extends JpaRepository<LoanItem, LoanItemId> {

    @Query("SELECT li, l, res FROM LoanItem li " +
            "JOIN Loan l ON li.loanId = l.loanId " +
            "JOIN Resource res ON li.resourceId = res.resourceId " +
            "WHERE li.loanId = :loanId AND li.deletedAt IS NULL")
    List<Object[]> findByLoanId(UUID loanId);

    @Query("SELECT li, l, res FROM LoanItem li " +
            "JOIN Loan l ON li.loanId = l.loanId " +
            "JOIN Resource res ON li.resourceId = res.resourceId " +
            "WHERE li.resourceId = :resourceId AND li.deletedAt IS NULL")
    List<Object[]> findByResourceId(UUID resourceId);

    @Modifying
    @Query("UPDATE LoanItem  li SET li.deletedAt = CURRENT_TIMESTAMP WHERE li.loanId = :loanId AND li.deletedAt IS NULL")
    void deleteByLoanId(UUID loanId);

    @Modifying
    @Query("UPDATE LoanItem li SET li.deletedAt = CURRENT_TIMESTAMP WHERE li.loanId = :loanId AND li.resourceId = :resourceId AND li.deletedAt IS NULL")
    void deleteByLoanIdAndResourceId(UUID loanId, UUID resourceId);

    @Query("SELECT CASE WHEN COUNT(li) > 0 THEN true ELSE false END FROM LoanItem  li WHERE li.loanId = :loanId AND li.resourceId = :resourceId AND li.deletedAt IS NULL")
    boolean existsByLoanIdAndResourceId(UUID loanId, UUID resourceId);
}
