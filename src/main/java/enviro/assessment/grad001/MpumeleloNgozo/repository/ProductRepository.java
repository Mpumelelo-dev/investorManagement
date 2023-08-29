package enviro.assessment.grad001.MpumeleloNgozo.repository;

import enviro.assessment.grad001.MpumeleloNgozo.dto.ProductDto;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Investor;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByInvestor(Investor investor);
}






