package enviro.assessment.grad001.MpumeleloNgozo.service;


import enviro.assessment.grad001.MpumeleloNgozo.ProductType.ProductType;
import enviro.assessment.grad001.MpumeleloNgozo.dto.ProductDto;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Investor;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Product;
import enviro.assessment.grad001.MpumeleloNgozo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getProductsForInvestor(Investor investor) {
        List<Product> products = productRepository.findByInvestor(investor);
        return products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElse(null);
    }

}
