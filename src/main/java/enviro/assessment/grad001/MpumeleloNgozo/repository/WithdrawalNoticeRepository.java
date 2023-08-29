package enviro.assessment.grad001.MpumeleloNgozo.repository;

import enviro.assessment.grad001.MpumeleloNgozo.entity.WithdrawalNotice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WithdrawalNoticeRepository extends JpaRepository<WithdrawalNotice, Long> {
    List<WithdrawalNotice> findByProduct_IdAndWithdrawalDateBetween(Long productId, LocalDate startDate, LocalDate endDate, PageRequest pageable);

}
