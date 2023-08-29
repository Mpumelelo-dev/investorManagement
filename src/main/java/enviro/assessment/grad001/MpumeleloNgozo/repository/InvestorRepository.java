package enviro.assessment.grad001.MpumeleloNgozo.repository;

import enviro.assessment.grad001.MpumeleloNgozo.entity.Investor;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Product;
import enviro.assessment.grad001.MpumeleloNgozo.entity.WithdrawalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
        Investor findByFirstNameAndLastNameAndId(String firstName, String lastName, String id);
}


