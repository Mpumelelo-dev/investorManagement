package enviro.assessment.grad001.MpumeleloNgozo.entity;

import enviro.assessment.grad001.MpumeleloNgozo.dto.WithdrawalNoticeDto;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class WithdrawalNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double withdrawalAmount=0.0;
    private LocalDate withdrawalDate;
    private String bankingDetails;

    @ManyToOne
    private Product product;


    public WithdrawalNotice() {
    }

    // Constructor that takes a WithdrawalNoticeDto as a parameter
    public WithdrawalNotice(WithdrawalNoticeDto withdrawalNoticeDto) {
        this.withdrawalAmount = withdrawalNoticeDto.getWithdrawalAmount();
        this.withdrawalDate = withdrawalNoticeDto.getWithdrawalDate();
        this.bankingDetails = withdrawalNoticeDto.getBankingDetails();


    }


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public String getBankingDetails() {
        return bankingDetails;
    }

    public void setBankingDetails(String bankingDetails) {
        this.bankingDetails = bankingDetails;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
