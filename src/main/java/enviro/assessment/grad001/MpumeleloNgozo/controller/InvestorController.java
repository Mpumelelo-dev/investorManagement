package enviro.assessment.grad001.MpumeleloNgozo.controller;

import enviro.assessment.grad001.MpumeleloNgozo.ProductType.ProductType;
import enviro.assessment.grad001.MpumeleloNgozo.dto.InvestorDto;
import enviro.assessment.grad001.MpumeleloNgozo.dto.ProductDto;
import enviro.assessment.grad001.MpumeleloNgozo.dto.WithdrawalNoticeDto;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Investor;
import enviro.assessment.grad001.MpumeleloNgozo.entity.Product;
import enviro.assessment.grad001.MpumeleloNgozo.entity.WithdrawalNotice;
import enviro.assessment.grad001.MpumeleloNgozo.repository.InvestorRepository;
import enviro.assessment.grad001.MpumeleloNgozo.repository.ProductRepository;
import enviro.assessment.grad001.MpumeleloNgozo.repository.WithdrawalNoticeRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.awt.print.Pageable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;




@RestController
@RequestMapping("/api/investors")
public class InvestorController {

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    // Endpoint to retrieve investor information and product list
    @GetMapping("/investor/{investorId}")
    public ResponseEntity<InvestorDto> getInvestorInformation(@PathVariable Long investorId) {
        Investor investor = investorRepository.findById(investorId)
                .orElseThrow(() -> new EntityNotFoundException("Investor not found"));

        // Retrieve products for the investor from the repository
        List<Product> products = productRepository.findByInvestor(investor);

        // Convert the list of Product entities to ProductDto objects
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());

        // Create InvestorDto using the investor and the list of ProductDto objects
        InvestorDto investorDto = new InvestorDto(investor, productDtos);

        return ResponseEntity.ok(investorDto);
    }

    // Endpoint to create a withdrawal notice
    @PostMapping("/withdrawal-notice")
    public ResponseEntity<?> createWithdrawalNotice(@RequestBody WithdrawalNoticeDto withdrawalNoticeDto) {
        // Perform validations
        Product product = productRepository.findById(withdrawalNoticeDto.getProductId())
                .orElse(null);

        // Check if the product exists
        if (product == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        // Validate withdrawal amount <= 90% of current balance
        double withdrawalAmount = withdrawalNoticeDto.getWithdrawalAmount();
        double currentBalance = product.getBalance();

        if (withdrawalAmount > 0.9 * currentBalance) {
            return ResponseEntity.badRequest().body("Withdrawal amount exceeds 90% of current balance");
        }

        // Validate product type
        if (product.getType().equals(ProductType.RETIREMENT) || product.getType().equals(ProductType.SAVINGS)) {

            LocalDate birthDate = investorRepository.findById(withdrawalNoticeDto.getInvestorId())
                    .map(Investor::getBirthDate)
                    .orElse(null);

            if (birthDate == null || birthDate.plusYears(65).isAfter(LocalDate.now())) {
                return ResponseEntity.badRequest().body("Investor must be at least 65 years old for a retirement product");
            }
        }

        // Create the withdrawal notice and save it to the repository
        WithdrawalNotice withdrawalNotice = new WithdrawalNotice(withdrawalNoticeDto);
        withdrawalNoticeRepository.save(withdrawalNotice);


        return ResponseEntity.ok("Withdrawal notice created successfully");
    }

    // Endpoint to download a statement of all notices for a selected product within a date range
    @GetMapping("/download-statement/{productId}")
    public ResponseEntity<byte[]> downloadStatementCSV(
            @PathVariable Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // Implement logic to retrieve withdrawal notices for the specified product and date range
        List<WithdrawalNotice> notices = retrieveWithdrawalNotices(productId, startDate, endDate);

        // Check if there are any notices
        if (notices.isEmpty()) {
            return ResponseEntity.badRequest().body("No withdrawal notices found for the specified criteria".getBytes());
        }

        // Generate CSV content based on the retrieved notices
        String csvContent = generateCSV(notices);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "withdrawal_notices.csv");

        // Convert CSV content to bytes
        byte[] csvBytes = csvContent.getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    // Implement logic to retrieve withdrawal notices based on the provided parameters
    private List<WithdrawalNotice> retrieveWithdrawalNotices(Long productId, LocalDate startDate, LocalDate endDate) {
        // Create a Pageable object to limit the number of results
        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Order.asc("withdrawalDate")));
        List<WithdrawalNotice> notices = withdrawalNoticeRepository
                .findByProduct_IdAndWithdrawalDateBetween(productId, startDate, endDate, pageable);

        return notices;
    }

    // Generate CSV content based on the retrieved notices
    private String generateCSV(List<WithdrawalNotice> notices) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(baos), CSVFormat.DEFAULT);

            // Write CSV header
            csvPrinter.printRecord("Withdrawal Amount", "Withdrawal Date", "Banking Details");

            // Write CSV records
            for (WithdrawalNotice notice : notices) {
                csvPrinter.printRecord(
                        notice.getWithdrawalAmount(),
                        notice.getWithdrawalDate(),
                        notice.getBankingDetails()
                );
            }

            csvPrinter.close();

            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "error!";
        }
    }

}
